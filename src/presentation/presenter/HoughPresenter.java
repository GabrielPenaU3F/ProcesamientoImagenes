package presentation.presenter;

import core.action.edgedetector.hough.CircleHoughTransformAction;
import core.action.edgedetector.hough.LineHoughTransformAction;
import domain.customimage.CustomImage;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.HoughSceneController;
import presentation.scenecreator.CannySceneCreator;

public class HoughPresenter {

    private final HoughSceneController view;
    //Este tiene que ser el mismo publish subject de la modified image para que pueda recibir el resultado de Canny
    private final PublishSubject<Image> imagePublishSubject;
    private final Observable<Image> cannyPublishSubject;
    private final LineHoughTransformAction lineHoughTransformAction;
    private final CircleHoughTransformAction circleHoughTransformAction;

    public HoughPresenter(HoughSceneController houghSceneController,
                          PublishSubject<Image> onModifiedImagePublishSubject,
                          Observable<Image> cannyPublishSubject,
                          LineHoughTransformAction lineHoughTransformAction,
                          CircleHoughTransformAction circleHoughTransformAction) {
        this.view = houghSceneController;
        this.imagePublishSubject = onModifiedImagePublishSubject;
        this.cannyPublishSubject = cannyPublishSubject;
        this.lineHoughTransformAction = lineHoughTransformAction;
        this.circleHoughTransformAction = circleHoughTransformAction;
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
            new CannySceneCreator().createScene();
            this.cannyPublishSubject.subscribe(image -> {
                //El formato deberia ser el mismo que tiene la imagen del repo, pero eso involucra pedirle la imagen, verificar que este, etc... no se si lo vale. Creo que no lo vale
                CustomImage customImage = new CustomImage(image, "png");
                CustomImage houghImage = this.circleHoughTransformAction.execute(customImage, xCenterDivisions, yCenterDivisions, radiusDivisions, tolerance);
                imagePublishSubject.onNext(houghImage.toFXImage());
                this.view.closeWindow();
            });

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
            new CannySceneCreator().createScene();
            this.cannyPublishSubject.subscribe(image -> {
                //El formato deberia ser el mismo que tiene la imagen del repo, pero eso involucra pedirle la imagen, verificar que este, etc... no se si lo vale. Creo que no lo vale
                CustomImage customImage = new CustomImage(image, "png");
                CustomImage houghImage = this.lineHoughTransformAction.execute(customImage, rhoDivisions, thetaDivisions, tolerance);
                imagePublishSubject.onNext(houghImage.toFXImage());
                this.view.closeWindow();
            });

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
