package presentation.presenter;

import domain.activecontour.ActiveContour;
import core.action.edgedetector.ApplyActiveContourAction;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.SelectionSquare;
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
            view.setImage(modifiedImage);
        });
    }

    public void onStart() {
        SelectionSquare selectionSquare = view.getSelectionSquare();
        if(selectionSquare.isValid()) {
            if (outsideGrayAverage != null && currentCustomImage != null) {
                Integer width = currentCustomImage.getWidth();
                Integer height = currentCustomImage.getHeight();
                activeContour = new ActiveContour(width, height, selectionSquare, outsideGrayAverage, objectGrayAverage);

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
        SelectionSquare selectionSquare = view.getSelectionSquare();
        if (selectionSquare.isValid()) {
            objectGrayAverage = getObjectGrayAverage(currentCustomImage, selectionSquare);
        } else {
            view.mustSelectArea();
        }
    }

    private int getObjectGrayAverage(CustomImage customImage, SelectionSquare selectionSquare) {
        int value = 0;
        int objectWidth = selectionSquare.getSecondRow() - selectionSquare.getFirstRow();
        int objectHeight = selectionSquare.getSecondColumn() - selectionSquare.getFirstColumn();
        for (int i = selectionSquare.getFirstRow() + 2; i <= selectionSquare.getSecondRow() - 2; i++) {
            for (int j = selectionSquare.getFirstColumn() + 2; j <= selectionSquare.getSecondColumn() - 2; j++) {
                value += customImage.getAverageValue(i, j);
            }
        }
        return value / (objectWidth * objectHeight);
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
        modifiedImage = contourCustomImage.drawActiveContour();
        view.setImage(modifiedImage);
    }
}
