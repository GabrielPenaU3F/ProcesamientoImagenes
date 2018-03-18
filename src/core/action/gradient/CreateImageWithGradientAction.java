package core.action.gradient;

import core.action.gradient.semaphore.GradientSemaphore;
import core.service.ImageGradientService;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class CreateImageWithGradientAction {

    private ImageGradientService imageGradientService;

    public CreateImageWithGradientAction(ImageGradientService imageGradientService) {
        this.imageGradientService = imageGradientService;
    }

    public Image execute() {
        switch (GradientSemaphore.getValue()) {
            case GREY:
                return this.imageGradientService.createGreyGradient();
            case COLOR:
                return this.imageGradientService.createColorGradient();
            default:
                return new WritableImage(1, 1);
        }
    }
}
