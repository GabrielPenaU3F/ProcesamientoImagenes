package core.action.noise;

import core.repository.ImageRepository;
import core.service.ImageOperationsService;
import core.service.statistics.RandomNumberGenerationService;
import domain.customimage.CustomImage;
import domain.customimage.Pixel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.List;

public class ApplyExponentialNoiseToImageAction {

    private final ImageRepository imageRepository;
    private final ImageOperationsService imageOperationsService;
    private final RandomNumberGenerationService randomNumberGenerationService;

    public ApplyExponentialNoiseToImageAction(ImageRepository imageRepository, ImageOperationsService imageOperationsService, RandomNumberGenerationService randomNumberGenerationService) {
        this.imageRepository = imageRepository;
        this.imageOperationsService = imageOperationsService;
        this.randomNumberGenerationService = randomNumberGenerationService;
    }

    public Image execute(double percent, double lambda) {
        if(!this.imageRepository.getImage().isPresent()) {
            return new WritableImage(100,100);
        }

        CustomImage customImage = this.imageRepository.getImage().get();
                int numberOfPixelsToContaminate = (int)(percent * customImage.getPixelQuantity());
        List<Pixel> pixelsToContaminate = customImage.pickNRandomPixels(numberOfPixelsToContaminate);

        //Generate a matrix where N cells contain noise, and thw rest contain zeros
        int[][] noiseMatrix = this.generateNoiseMatrix(lambda, customImage, pixelsToContaminate);

        //Now, we multiply the noise matrix and the image and normalize the scale (for each channel)
        int[][] newRedLevelMatrix = this.imageOperationsService.adjustScale(this.imageOperationsService.displacePixelsValues(this.multiplyImageRedChannelAndNoiseMatrix(customImage, noiseMatrix)), pixelsToContaminate);
        int[][] newGreenLevelMatrix = this.imageOperationsService.adjustScale(this.imageOperationsService.displacePixelsValues(this.multiplyImageGreenChannelAndNoiseMatrix(customImage, noiseMatrix)), pixelsToContaminate);
        int[][] newBlueLevelMatrix = this.imageOperationsService.adjustScale(this.imageOperationsService.displacePixelsValues(this.multiplyImageBlueChannelAndNoiseMatrix(customImage, noiseMatrix)), pixelsToContaminate);

        //Finally, we write the resultant matrix to a new image
        return this.imageOperationsService.writeNewPixelsValuesToImage(newRedLevelMatrix, newGreenLevelMatrix, newBlueLevelMatrix);

    }

    private int[][] multiplyImageRedChannelAndNoiseMatrix(CustomImage customImage, int[][] noiseMatrix) {
        Image image = SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        PixelReader reader = image.getPixelReader();
        int[][] productMatrix = new int[customImage.getWidth()][customImage.getHeight()];
        for (int i=0; i < customImage.getWidth(); i ++) {
            for (int j=0; j < customImage.getHeight(); j++) {
                productMatrix[i][j] = ((int)(reader.getColor(i,j).getRed()*255)) * noiseMatrix[i][j];
            }
        }
        return productMatrix;
    }

    private int[][] multiplyImageGreenChannelAndNoiseMatrix(CustomImage customImage, int[][] noiseMatrix) {

        Image image = SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        PixelReader reader = image.getPixelReader();
        int[][] productMatrix = new int[customImage.getWidth()][customImage.getHeight()];
        for (int i=0; i < customImage.getWidth(); i ++) {
            for (int j=0; j < customImage.getHeight(); j++) {
                productMatrix[i][j] = ((int)(reader.getColor(i,j).getGreen()*255)) * noiseMatrix[i][j];
            }
        }
        return productMatrix;

    }

    private int[][] multiplyImageBlueChannelAndNoiseMatrix(CustomImage customImage, int[][] noiseMatrix) {

        Image image = SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        PixelReader reader = image.getPixelReader();
        int[][] productMatrix = new int[customImage.getWidth()][customImage.getHeight()];
        for (int i=0; i < customImage.getWidth(); i ++) {
            for (int j=0; j < customImage.getHeight(); j++) {
                productMatrix[i][j] = ((int)(reader.getColor(i,j).getBlue()*255)) * noiseMatrix[i][j];
            }
        }
        return productMatrix;

    }

    private int[][] generateNoiseMatrix(double lambda, CustomImage customImage, List<Pixel> pixelsToContaminate) {
        int[][] noiseMatrix = new int[customImage.getWidth()][customImage.getHeight()];
        for (int i=0; i < noiseMatrix.length; i++) {
            for (int j=0; j < noiseMatrix[i].length; j++) {
                noiseMatrix[i][j] = 1; //Since the noise is multiplicative, the not-noise cells must contain 1
            }
        }
        for (Pixel pixel : pixelsToContaminate) {
            int i = pixel.getX();
            int j = pixel.getY();
            noiseMatrix[i][j] = (int) (this.randomNumberGenerationService.generateExponentialNumber(lambda));
        }
        return noiseMatrix;
    }

}
