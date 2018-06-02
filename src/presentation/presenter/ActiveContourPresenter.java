package presentation.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import core.action.edgedetector.ApplyActiveContourAction;
import core.action.edgedetector.ApplyActiveContourToImageSequenceAction;
import core.action.edgedetector.GetImageSequenceAction;
import core.action.image.GetImageAction;
import domain.activecontour.ActiveContour;
import domain.activecontour.ActiveContourMode;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.SelectionSquare;
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
    private final GetImageSequenceAction getImageSequenceAction;
    private final ApplyActiveContourToImageSequenceAction applyActiveContourToImageSequenceAction;
    private final PublishSubject<Image> onModifiedImagePublishSubject;

    private Integer objectGrayAverage;
    private Integer outsideGrayAverage;
    private CustomImage currentCustomImage;
    private ActiveContour activeContour;
    private Image modifiedImage;
    private List<CustomImage> currentImages;

    public ActiveContourPresenter(ActiveContourSceneController view,
            ApplyActiveContourAction applyActiveContourAction, GetImageAction getImageAction,
            GetImageSequenceAction getImageSequenceAction,
            ApplyActiveContourToImageSequenceAction applyActiveContourToImageSequenceAction,
            PublishSubject<Image> onModifiedImagePublishSubject) {
        this.view = view;
        this.applyActiveContourAction = applyActiveContourAction;
        this.getImageAction = getImageAction;
        this.getImageSequenceAction = getImageSequenceAction;
        this.applyActiveContourToImageSequenceAction = applyActiveContourToImageSequenceAction;
        this.onModifiedImagePublishSubject = onModifiedImagePublishSubject;
        this.currentImages = new ArrayList<>();
    }

    public void initialize(boolean isSingle) {
        view.setInitializeCustomImageView(new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withSelectionMode());

        onInitializeContours(isSingle);
    }

    public void onInitializeContours(boolean isSingle) {
        if (isSingle) {
            view.disableApplyButton();
            view.enableStartButton();
            this.getImageAction.execute().ifPresent(customImage -> {
                currentCustomImage = customImage;
                modifiedImage = customImage.toFXImage();
                view.setImage(modifiedImage);
            });
        } else {
            view.disableStartButton();
            this.getImageSequenceAction.execute().ifPresent(customImages -> {
                if (!customImages.isEmpty()) {
                    currentCustomImage = customImages.get(0);
                    modifiedImage = currentCustomImage.toFXImage();
                    currentImages = customImages;
                    view.setImage(modifiedImage);
                }
            });
        }
    }

    public void onStart() {
        SelectionSquare selectionSquare = view.getSelectionSquare();
        if (selectionSquare.isValid() && outsideGrayAverage != null) {
            if (ActiveContourMode.isSingle()) {
                onStartSingleMode(selectionSquare);
            } else {
                onStartSequenceMode(selectionSquare);
                // start image sequence !
                // falta el setImageSequence y todo el dialogo para cargar las imagenes
            }
        } else {
            view.mustSelectArea();
        }

        view.disableStartButton();
    }

    private void onStartSequenceMode(SelectionSquare selectionSquare) {
        if (!currentImages.isEmpty()) {
            activeContour = createActiveContour(selectionSquare, currentCustomImage);
            applyActiveContourToImageSequenceAction.execute(currentImages, activeContour, view.getSteps())
                                                   .delay(3, TimeUnit.SECONDS)
                                                   .subscribe(customImage -> view.setImage(customImage.toFXImage()));
        }
    }

    private void onStartSingleMode(SelectionSquare selectionSquare) {
        if (currentCustomImage != null) {
            activeContour = createActiveContour(selectionSquare, currentCustomImage);
            setCurrentContourCustomImage(applyActiveContourAction.execute(currentCustomImage, activeContour, 1));
        }
        view.enableApplyButton();
    }

    private ActiveContour createActiveContour(SelectionSquare selectionSquare, CustomImage customImage) {
        return new ActiveContour(customImage.getWidth(), customImage.getHeight(), selectionSquare, outsideGrayAverage, objectGrayAverage);
    }

    public void onApply() {
        if (view.getSteps() > 0) {
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
