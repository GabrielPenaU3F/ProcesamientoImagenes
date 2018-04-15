package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import presentation.presenter.EqualizedImagePresenter;

public class EqualizedImageSceneController {

    @FXML
    public BarChart barChart;
    public XYChart.Series data;

    @FXML
    public ImageView equalizedImageView;

    private final EqualizedImagePresenter equalizedImagePresenter;

    public EqualizedImageSceneController() {
        this.equalizedImagePresenter = PresenterProvider.provideEqualizedImagePresenter(this);
    }

    @FXML
    public void initialize() {
        this.data = new XYChart.Series();
        this.equalizedImagePresenter.onInitializeView();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.equalizedImageView.getScene().getWindow();
        stage.close();
    }
}
