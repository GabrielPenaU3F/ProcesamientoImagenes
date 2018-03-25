package core.action.figure;

import core.action.figure.semaphore.FigureSemaphore;
import core.service.ImageFigureService;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class CreateImageWithFigureAction {

    private final ImageFigureService imageFigureService;

    public CreateImageWithFigureAction(ImageFigureService imageFigureService) {
        this.imageFigureService = imageFigureService;
    }

    public Image execute(int width, int height) {

        switch (FigureSemaphore.getValue()) {
            case CIRCLE:
                return imageFigureService.createImageWithCircle(width, height);
            case QUADRATE:
                return imageFigureService.createImageWithQuadrate(width, height);
            default:
                return new WritableImage(width, height);
        }
    }
}
