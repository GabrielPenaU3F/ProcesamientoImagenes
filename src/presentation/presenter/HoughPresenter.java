package presentation.presenter;

import core.action.edgedetector.ApplyEdgeDetectorByGradientAction;
import core.action.edgedetector.hough.CircleHoughTransformAction;
import core.action.edgedetector.hough.LineHoughTransformAction;
import core.action.image.GetImageAction;
import core.action.threshold.ApplyOtsuThresholdEstimationAction;
import domain.automaticthreshold.OtsuThresholdResult;
import domain.customimage.CustomImage;
import domain.mask.prewitt.PrewittXDerivativeMask;
import domain.mask.prewitt.PrewittYDerivativeMask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.HoughSceneController;


public class HoughPresenter {

    private final HoughSceneController view;
    //Este tiene que ser el mismo publish subject de la modified image para que pueda recibir el resultado de Canny
    private final PublishSubject<Image> imagePublishSubject;
    private final LineHoughTransformAction lineHoughTransformAction;
    private final CircleHoughTransformAction circleHoughTransformAction;
    private final ApplyOtsuThresholdEstimationAction applyOtsuThresholdEstimationAction;
    private final GetImageAction getImageAction;
    private final ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction;

    public HoughPresenter(HoughSceneController houghSceneController,
                          PublishSubject<Image> onModifiedImagePublishSubject,
                          LineHoughTransformAction lineHoughTransformAction,
                          CircleHoughTransformAction circleHoughTransformAction,
                          ApplyOtsuThresholdEstimationAction applyOtsuThresholdEstimationAction,
                          GetImageAction getImageAction,
                          ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction) {
        this.view = houghSceneController;
        this.imagePublishSubject = onModifiedImagePublishSubject;
        this.lineHoughTransformAction = lineHoughTransformAction;
        this.circleHoughTransformAction = circleHoughTransformAction;
        this.getImageAction = getImageAction;
        this.applyOtsuThresholdEstimationAction = applyOtsuThresholdEstimationAction;
        this.applyEdgeDetectorByGradientAction = applyEdgeDetectorByGradientAction;
    }

    public void onApply() {

        if (view.lineRadioButton.isSelected()) this.onHoughTransformForLines();
        else if (view.circleRadioButton.isSelected()) this.onHoughTransformForCircles();

    }

    private void onHoughTransformForCircles() {

        int xCenterDivisions = Integer.parseInt(view.circleXCenterTextField.getText());
        int yCenterDivisions = Integer.parseInt(view.circleYCenterTextField.getText());
        int radiusDivisions = Integer.parseInt(view.circleRadiusTextField.getText());
        double tolerance = Double.parseDouble(view.toleranceTextField.getText());

        if (isXCenterValid(xCenterDivisions) && isYCenterValid(yCenterDivisions) && isRadiusValid(radiusDivisions) && isToleranceValid(tolerance)) {

            this.getImageAction.execute().ifPresent(customImage -> {
                CustomImage edgedImage = new CustomImage(this.applyEdgeDetectorByGradientAction.execute(customImage, new PrewittXDerivativeMask(), new PrewittYDerivativeMask()), "png");
                OtsuThresholdResult otsuThresholdResult = this.applyOtsuThresholdEstimationAction.execute(edgedImage);
                CustomImage thresholdizedImage = new CustomImage(otsuThresholdResult.getImage(), "png");
                CustomImage houghImage = this.circleHoughTransformAction.execute(customImage, thresholdizedImage, xCenterDivisions, yCenterDivisions, radiusDivisions, tolerance);
                imagePublishSubject.onNext(houghImage.toFXImage());
                this.view.closeWindow();
                    }
            );

        }

    }

    private boolean isRadiusValid(int radiusDivisions) {
        return radiusDivisions > 0;
    }

    private boolean isYCenterValid(int yCenterDivisions) {
        return yCenterDivisions > 0;
    }

    private boolean isXCenterValid(int xCenterDivisions) {
        return xCenterDivisions > 0;
    }

    private void onHoughTransformForLines() {

        int rhoDivisions = Integer.parseInt(view.lineRhoTextField.getText());
        int thetaDivisions = Integer.parseInt(view.lineThetaTextField.getText());
        double tolerance = Double.parseDouble(view.toleranceTextField.getText());

        if (isRhoValid(rhoDivisions) && isThetaValid(thetaDivisions) && isToleranceValid(tolerance)) {

            this.getImageAction.execute().ifPresent(customImage -> {
                CustomImage edgedImage = new CustomImage(this.applyEdgeDetectorByGradientAction.execute(customImage, new PrewittXDerivativeMask(), new PrewittYDerivativeMask()), "png");
                OtsuThresholdResult otsuThresholdResult = this.applyOtsuThresholdEstimationAction.execute(edgedImage);
                CustomImage thresholdizedImage = new CustomImage(otsuThresholdResult.getImage(), "png");
                CustomImage houghImage = this.lineHoughTransformAction.execute(customImage, thresholdizedImage, rhoDivisions, thetaDivisions, tolerance);
                imagePublishSubject.onNext(houghImage.toFXImage());
                this.view.closeWindow();
                    }
            );

        }
    }

    private boolean isThetaValid(int thetaDivisions) {
        return thetaDivisions > 0;
    }

    private boolean isRhoValid(int rhoDivisions) {
        return rhoDivisions > 0;
    }

    private boolean isToleranceValid(double tolerance) {
        return tolerance > 0;
    }
}
