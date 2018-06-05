package presentation.presenter;

import java.util.ArrayList;
import java.util.List;

import core.action.edgedetector.ApplyActiveContourAction;
import core.action.edgedetector.ApplyActiveContourOnImageSequenceAction;
import core.action.edgedetector.GetImageSequenceAction;
import core.action.image.GetImageAction;
import domain.activecontour.ActiveContour;
import domain.activecontour.ActiveContourMode;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.FdFunctionMode;
import domain.activecontour.SelectionSquare;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import presentation.controller.ActiveContourSceneController;
import presentation.view.CustomImageView;

public class ActiveContourPresenter {

    private static final int STARTER_STEPS = 1;
    private static final double DEFAULT_EPSILON = 0;

    private final ActiveContourSceneController view;
    private final GetImageAction getImageAction;
    private final GetImageSequenceAction getImageSequenceAction;
    private final ApplyActiveContourAction applyActiveContourAction;
    private final ApplyActiveContourOnImageSequenceAction applyActiveContourOnImageSequenceAction;
    private final PublishSubject<Image> onModifiedImagePublishSubject;

    private Integer objectGrayAverage;
    private Integer outsideGrayAverage;
    private CustomImage currentCustomImage;
    private ActiveContour activeContour;
    private Image modifiedImage;
    private List<CustomImage> currentImages;
    private List<ContourCustomImage> contours;
    private int contourIndex = 0;

    public ActiveContourPresenter(ActiveContourSceneController view,
            ApplyActiveContourAction applyActiveContourAction, GetImageAction getImageAction,
            GetImageSequenceAction getImageSequenceAction,
            ApplyActiveContourOnImageSequenceAction applyActiveContourOnImageSequenceAction,
            PublishSubject<Image> onModifiedImagePublishSubject) {
        this.view = view;
        this.applyActiveContourAction = applyActiveContourAction;
        this.getImageAction = getImageAction;
        this.getImageSequenceAction = getImageSequenceAction;
        this.applyActiveContourOnImageSequenceAction = applyActiveContourOnImageSequenceAction;
        this.onModifiedImagePublishSubject = onModifiedImagePublishSubject;
        this.currentImages = new ArrayList<>();
        this.contours = new ArrayList<>();
    }

    public void initialize() {
        view.setInitializeCustomImageView(new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withSelectionMode());

        onInitializeContours();
        FdFunctionMode.classic();
    }

    public void onInitializeContours() {
        if (ActiveContourMode.isSingle()) {
            this.getImageAction.execute().ifPresent(customImage -> {
                currentCustomImage = customImage;
                modifiedImage = customImage.toFXImage();
                view.setImage(modifiedImage);
            });
        } else {
            this.contourIndex = 0;
            this.getImageSequenceAction.execute().ifPresent(customImages -> {
                if (!customImages.isEmpty()) {
                    currentCustomImage = customImages.get(0);
                    modifiedImage = currentCustomImage.toFXImage();
                    currentImages = customImages;
                    view.setImage(modifiedImage);
                }
            });
        }

        view.disablePrevButton();
        view.disableNextButton();
        view.disableApplyButton();
        view.enableStartButton();
    }

    public void onStart() {
        SelectionSquare selectionSquare = view.getSelectionSquare();
        if (selectionSquare.isValid() && outsideGrayAverage != null) {
            if (ActiveContourMode.isSingle()) {
                onStartSingleMode(selectionSquare);
                view.enableApplyButton();
            } else {
                onStartSequenceMode(selectionSquare);
            }

            view.disableStartButton();
        } else {
            view.mustSelectArea();
        }
    }

    private void onStartSingleMode(SelectionSquare selectionSquare) {
        if (currentCustomImage != null) {
            activeContour = createActiveContour(selectionSquare, currentCustomImage);
            setCurrentContourCustomImage(applyActiveContourAction.execute(currentCustomImage, activeContour, STARTER_STEPS, DEFAULT_EPSILON));
        }
    }

    private void onStartSequenceMode(SelectionSquare selectionSquare) {
        if (currentImages != null && !currentImages.isEmpty()) {
            activeContour = createActiveContour(selectionSquare, currentCustomImage);
            contours = applyActiveContourOnImageSequenceAction.execute(currentImages, activeContour, view.getSteps(), view.getEpsilon());
            view.setImage(contours.get(contourIndex).drawActiveContour());
            contourIndex++;
        }

        view.enableNextButton();
    }

    private ActiveContour createActiveContour(SelectionSquare selectionSquare, CustomImage customImage) {
        return new ActiveContour(customImage.getWidth(), customImage.getHeight(), selectionSquare, outsideGrayAverage, objectGrayAverage);
    }

    public void onApply() {
        if (view.getSteps() > 0) {
            if (outsideGrayAverage != null && currentCustomImage != null) {
                setCurrentContourCustomImage(applyActiveContourAction.execute(currentCustomImage, activeContour, view.getSteps(), view.getEpsilon()));
            }
        } else {
            view.stepsMustBeGreaterThanZero();
        }
    }

    public void onPrev() {
        if (!contours.isEmpty() && contourIndex > 0) {
            contourIndex--;
            view.setImage(contours.get(contourIndex).drawActiveContour());
            view.enableNextButton();
        } else {
            view.disablePrevButton();
        }
    }

    public void onNext() {
        if (!contours.isEmpty() && contourIndex < contours.size()) {
            view.setImage(contours.get(contourIndex).drawActiveContour());
            view.enablePrevButton();
            contourIndex++;
        } else {
            view.disableNextButton();
        }
    }

    public void onGetInsidePressed() {
        SelectionSquare selectionSquare = view.getSelectionSquare();
        if (selectionSquare.isValid()) {
            objectGrayAverage = getObjectGrayAverage(currentCustomImage, selectionSquare);
            view.disableGetObjectButton();
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
                double value1 = Math.round((color.getRed() + color.getGreen() + color.getBlue()) / 3);
                value += value1;
            }
        }

        outsideGrayAverage = value / (width * height);
        view.disableGetBackgroundButton();
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
