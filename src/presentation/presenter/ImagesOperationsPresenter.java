package presentation.presenter;

import core.action.image.LoadImageAction;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImagesOperationsPresenter {

    private final LoadImageAction loadImageAction;
    private CustomImage image1;
    private CustomImage image2;

    public ImagesOperationsPresenter(LoadImageAction loadImageAction) {
        this.loadImageAction = loadImageAction;
    }

    public Image onloadImage1() {
        this.image1 = this.loadImageAction.execute();
        return SwingFXUtils.toFXImage(this.image1.getBufferedImage(), null );
    }

    public Image onloadImage2() {
        this.image2 = this.loadImageAction.execute();
        return SwingFXUtils.toFXImage(this.image2.getBufferedImage(), null );
    }

    public Image onMakeImagesSum() {
        int resultantImageWidth = this.calculateWidth();
        int resultantImageHeight = this.calculateHeight();
        int [][][] auxImage1 = new int[resultantImageWidth][resultantImageHeight][3];
        int [][][] auxImage2 = new int[resultantImageWidth][resultantImageHeight][3];
        this.fillAuxImages(auxImage1, auxImage2);
        int[][][] resultantMatrix = this.sumAuxImages(auxImage1, auxImage2, resultantImageWidth, resultantImageHeight);
        int resultantRedImageR = this.calculateR(resultantMatrix, 0);//Red
        int resultantGreenImageR = this.calculateR(resultantMatrix, 1);//Green
        int resultantBlueImageR = this.calculateR(resultantMatrix, 2);//Blue
        WritableImage resultantImage = new WritableImage(resultantImageWidth, resultantImageHeight);
        this.writeNewPixelsValuesInImage(resultantMatrix, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
    }

    public Image onMakeImagesMultiplication() {
        int resultantImageWidth = this.calculateWidth();
        int resultantImageHeight = this.calculateHeight();
        int [][][] auxImage1 = new int[resultantImageWidth][resultantImageHeight][3];
        int [][][] auxImage2 = new int[resultantImageWidth][resultantImageHeight][3];
        this.fillAuxImages(auxImage1, auxImage2);
        int[][][] resultantImageRepresentation = this.multiplicateAuxImages(auxImage1, auxImage2, resultantImageWidth, resultantImageHeight);
        int resultantRedImageR = this.calculateR(resultantImageRepresentation, 0);//Red
        int resultantGreenImageR = this.calculateR(resultantImageRepresentation, 1);//Green
        int resultantBlueImageR = this.calculateR(resultantImageRepresentation, 2);//Blue
        WritableImage resultantImage = new WritableImage(resultantImageWidth, resultantImageHeight);
        this.writeNewPixelsValuesInImage(resultantImageRepresentation, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
    }

    private int[][][] multiplicateAuxImages(int[][][] auxImage1, int[][][] auxImage2, int width, int height){
        int[][][] resultant_image = new int[width][height][3];
        for (int i = 0; i < auxImage1.length; i++){
            for (int j = 0; j < auxImage1[i].length; j++){
                for (int k = 0; k < auxImage1[i][j].length; k++){
                    resultant_image[i][j][k] = auxImage1[i][j][k] * auxImage2[i][j][k];
                }
            }
        }
        return resultant_image;
    }

    private void fillAuxImages(int[][][] auxImage1, int[][][] auxImage2) {
        this.completeWithZero(auxImage1);
        this.completeWithZero(auxImage2);
        this.setChannelsPixelsValuesInAuxImage(auxImage1,this.image1);
        this.setChannelsPixelsValuesInAuxImage(auxImage2,this.image2);
    }

    private int calculateWidth(){
        int resultantImageWidth = 1;
        if (this.image1.getWidth() > this.image2.getWidth()) {
            resultantImageWidth = this.image1.getWidth();
        } else if (this.image2.getWidth() >= this.image1.getWidth()) {
            resultantImageWidth = this.image2.getWidth();
        }
        return resultantImageWidth;
    }

    private int calculateHeight(){
        int resultantImageHeight = 1;
        if (this.image1.getHeight() > this.image2.getHeight()) {
            resultantImageHeight = this.image1.getHeight();
        } else if (this.image2.getHeight() >= this.image1.getHeight()) {
            resultantImageHeight = this.image2.getHeight();
        }
        return resultantImageHeight;
    }

    private void completeWithZero(int[][][] auxImage){
        for (int i = 0; i < auxImage.length; i++){
            for (int j = 0; j < auxImage[i].length; j++){
                for (int k = 0; k < auxImage[i][j].length; k++){
                    auxImage[i][j][k] = 0;
                }
            }
        }
    }

    private void setChannelsPixelsValuesInAuxImage(int[][][] auxImage, CustomImage image){
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                for (int k = 0; k < auxImage[i][j].length; k++){
                    if(k == 0){
                        auxImage[i][j][k] += image.getRChannelValue(i,j);
                    }
                    else if(k == 1){
                        auxImage[i][j][k] += image.getGChannelValue(i,j);
                    }
                    else if(k == 2){
                        auxImage[i][j][k] += image.getBChannelValue(i,j);
                    }
                }
            }
        }
    }

    private int[][][] sumAuxImages(int[][][] auxImage1, int[][][] auxImage2, int width, int height){ //ambas matrices tienen el mismo tamanio
        int[][][] resultant_image = new int[width][height][3];
        for (int i = 0; i < auxImage1.length; i++){
            for (int j = 0; j < auxImage1[i].length; j++){
                for (int k = 0; k < auxImage1[i][j].length; k++){
                    resultant_image[i][j][k] = auxImage1[i][j][k] + auxImage2[i][j][k];
                }
            }
        }
        return resultant_image;
    }

    private int calculateR(int[][][] auxImage, int channel){
        int actualR = 0;
        for (int i = 0; i < auxImage.length; i++){
            for (int j = 0; j < auxImage[i].length; j++){
                if(auxImage[i][j][channel] > actualR){
                    actualR = auxImage[i][j][channel];
                }
            }
        }
        return actualR;
    }

    private void writeNewPixelsValuesInImage(int[][][] resultantImageRepresentation, int imageRedR, int imageGreenR, int imageBlueR, WritableImage image){
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < resultantImageRepresentation.length; i++){
            for (int j = 0; j < resultantImageRepresentation[i].length; j++){
                int redPixelValue = 0;
                int greenPixelValue = 0;
                int bluePixelValue = 0;
                for (int k = 0; k < resultantImageRepresentation[i][j].length; k++){
                    if(k == 0){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageRedR);
                        redPixelValue = (int) result;
                    }
                    else if(k == 1){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageGreenR);
                        greenPixelValue = (int) result;
                    }
                    else if(k == 2){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageBlueR);
                        bluePixelValue = (int) result;
                    }
                }
                Color color = Color.rgb(redPixelValue,greenPixelValue,bluePixelValue);
                pixelWriter.setColor(i,j,color);
            }
        }
    }

}

