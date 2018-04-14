package core.action.figure;

import core.repository.ImageRepository;
import core.service.generation.ImageFigureService;
import domain.generation.Figure;
import domain.customimage.CustomImage;
import domain.customimage.Format;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class CreateImageWithFigureAction {

    private final ImageFigureService imageFigureService;
    private final ImageRepository imageRepository;

    public CreateImageWithFigureAction(ImageFigureService imageFigureService, ImageRepository imageRepository) {
        this.imageFigureService = imageFigureService;
        this.imageRepository = imageRepository;
    }

    public CustomImage execute(int width, int height, Figure value) {

        switch (value) {
            case CIRCLE:
                Image imageWithCircle = imageFigureService.createImageWithCircle(width, height);
                return putOnRepository(SwingFXUtils.fromFXImage(imageWithCircle, null));
            case QUADRATE:
                Image imageWithQuadrate = imageFigureService.createImageWithQuadrate(width, height);
                return putOnRepository(SwingFXUtils.fromFXImage(imageWithQuadrate, null));
            default:
                return CustomImage.EMPTY;
        }
    }

    private CustomImage putOnRepository(BufferedImage bufferedImage) {
        return imageRepository.saveImage(new CustomImage(bufferedImage, Format.PNG));
    }
}
