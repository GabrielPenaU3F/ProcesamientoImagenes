package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import presentation.presenter.ImageReportPresenter;

import java.text.DecimalFormat;

public class ImageInformSceneController {

    @FXML
    private Label avgBandR;
    @FXML
    private Label avgBandG;
    @FXML
    private Label avgBandB;
    @FXML
    private Label avgBandGrey;
    @FXML
    private Label totalPixel;

    private final ImageReportPresenter imageReportPresenter;

    public ImageInformSceneController() {
        this.imageReportPresenter = PresenterProvider.provideImageInformPresenter();
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        this.imageReportPresenter.createImageInform().ifPresent(imageInform -> {
            avgBandR.setText(new DecimalFormat("#.####").format(imageInform.getAverageBandR()));
            avgBandG.setText(new DecimalFormat("#.####").format(imageInform.getAverageBandG()));
            avgBandB.setText(new DecimalFormat("#.####").format(imageInform.getAverageBandB()));
            avgBandGrey.setText(new DecimalFormat("#.####").format(imageInform.getAverageBandGray()));
            totalPixel.setText(imageInform.getTotalPixel().toString());
        });
    }
}
