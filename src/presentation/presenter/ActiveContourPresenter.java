package presentation.presenter;

import core.action.edgedetector.activecontour.ApplyActiveContourAction;
import core.action.edgedetector.activecontour.Corners;
import core.action.image.GetImageAction;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import presentation.controller.ActiveContourSceneController;
import presentation.view.CustomImageView;

public class ActiveContourPresenter {

    private final ActiveContourSceneController view;
    private final ApplyActiveContourAction applyActiveContourAction;
    private final GetImageAction getImageAction;

    private Corners corners;
    private Integer outsideGrayAverage;
    private CustomImage currentCustomImage;

    public ActiveContourPresenter(ActiveContourSceneController view,
            ApplyActiveContourAction applyActiveContourAction, GetImageAction getImageAction) {
        this.view = view;
        this.applyActiveContourAction = applyActiveContourAction;
        this.getImageAction = getImageAction;
    }

    public void initialize() {
        view.setInitializeCustomImageView(new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withSelectionMode());

        onInitializeContours();
    }

    public void onInitializeContours() {
        this.getImageAction.execute().ifPresent(customImage -> setCurrentImage(customImage));
    }

    public void onApply() {
        if (corners != null && outsideGrayAverage != null && currentCustomImage != null) {
            setCurrentImage(applyActiveContourAction.execute(currentCustomImage, corners, outsideGrayAverage, view.getSteps()));
        }
    }

    public void onGetInsidePressed() {
        corners = view.getCorners();
    }

    public void onGetOutsidePressed() {

        Image image = view.getPartialImage();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        int value = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = reader.getColor(x, y);
                value += ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
            }
        }

        outsideGrayAverage = value / (width * height);
    }

    private void setCurrentImage(CustomImage customImage) {
        currentCustomImage = customImage;
        view.setImage(customImage.toFXImage());
    }
}
