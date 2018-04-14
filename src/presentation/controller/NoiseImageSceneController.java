package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import presentation.presenter.NoiseImagePresenter;

public class NoiseImageSceneController {

    @FXML
    public BarChart barChart;
    public XYChart.Series data;
    @FXML
    public ImageView noiseImageView;

    private final NoiseImagePresenter noiseImagePresenter;

    public NoiseImageSceneController() {
        this.noiseImagePresenter = PresenterProvider.provideNoiseImagePresenter(this);
    }

    @FXML
    public void initialize() {
        data = new XYChart.Series();
        this.noiseImagePresenter.initialize();
    }
}
