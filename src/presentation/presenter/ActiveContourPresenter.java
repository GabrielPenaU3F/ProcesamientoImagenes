package presentation.presenter;

import core.action.edgedetector.activecontour.ActiveContour;
import core.action.edgedetector.activecontour.ApplyActiveContourAction;
import core.action.edgedetector.activecontour.ContourCustomImage;
import core.action.edgedetector.activecontour.Corners;
import core.action.image.GetImageAction;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import presentation.controller.ActiveContourSceneController;
import presentation.view.CustomImageView;

public class ActiveContourPresenter {

    private final ActiveContourSceneController view;
    private final ApplyActiveContourAction applyActiveContourAction;
    private final GetImageAction getImageAction;
    private final PublishSubject<Image> onModifiedImagePublishSubject;

    private Integer objectGrayAverage;
    private Integer outsideGrayAverage;
    private CustomImage currentCustomImage;
    private ActiveContour activeContour;
    private Image modifiedImage;

    public ActiveContourPresenter(ActiveContourSceneController view,
            ApplyActiveContourAction applyActiveContourAction, GetImageAction getImageAction,
            PublishSubject<Image> onModifiedImagePublishSubject) {
        this.view = view;
        this.applyActiveContourAction = applyActiveContourAction;
        this.getImageAction = getImageAction;
        this.onModifiedImagePublishSubject = onModifiedImagePublishSubject;
    }

    public void initialize() {
        view.setInitializeCustomImageView(new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withSelectionMode());

        onInitializeContours();
    }

    public void onInitializeContours() {
        this.getImageAction.execute().ifPresent(customImage -> {
            currentCustomImage = customImage;
            modifiedImage = customImage.toFXImage();
            view.setImage(customImage.toFXImage());
        });
    }

    public void onStart() {
        Corners corners = view.getCorners();
        if(corners.isValid()) {
            if (outsideGrayAverage != null && currentCustomImage != null) {
                Integer width = currentCustomImage.getWidth();
                Integer height = currentCustomImage.getHeight();
                activeContour = new ActiveContour(width, height, corners, outsideGrayAverage, objectGrayAverage);

                setCurrentContourCustomImage(applyActiveContourAction.execute(currentCustomImage, activeContour, 1));
            }
        } else {
            view.mustSelectArea();
        }
    }

    public void onApply() {
        if(view.getSteps() > 0) {
            if (outsideGrayAverage != null && currentCustomImage != null) {
                setCurrentContourCustomImage(applyActiveContourAction.execute(currentCustomImage, activeContour, view.getSteps()));
            }
        } else {
            view.stepsMustBeGreaterThanZero();
        }
    }

    public void onGetInsidePressed() {
        Corners corners = view.getCorners();
        if (corners.isValid()) {
            objectGrayAverage = getObjectGrayAverage(currentCustomImage, corners);
        } else {
            view.mustSelectArea();
        }
    }

    private int getObjectGrayAverage(CustomImage customImage, Corners corners) {
        int value = 0;
        int widthObject = corners.getSecondRow() - corners.getFirstRow();
        int heightObject = corners.getSecondColumn() - corners.getFirstColumn();
        for (int i = corners.getFirstRow() + 2; i <= corners.getSecondRow() - 2; i++) {
            for (int j = corners.getFirstColumn() + 2; j <= corners.getSecondColumn() - 2; j++) {
                value += customImage.getAverageValue(i, j);
            }
        }
        return value / (widthObject * heightObject);
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

    public void onFinish() {
        this.onModifiedImagePublishSubject.onNext(modifiedImage);
        view.closeWindow();
    }

    private void setCurrentContourCustomImage(ContourCustomImage contourCustomImage) {
        activeContour = contourCustomImage.getActiveContour();
        modifiedImage = contourCustomImage.drawActiveContour().toFXImage();
        view.setImage(modifiedImage);
    }
}
