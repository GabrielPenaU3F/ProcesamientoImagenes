package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageHistogramPresenter;

public class ImageHistogramSceneController {

    @FXML
    public ImageView imageView;
    @FXML
    public BarChart barChart;
    public XYChart.Series data;

    private final ImageHistogramPresenter imageHistogramPresenter;

    public ImageHistogramSceneController() {
        data = new XYChart.Series();
        this.imageHistogramPresenter = PresenterProvider.provideImageHistogramPresenter(this);
    }

    @FXML
    public void initialize() {
        imageHistogramPresenter.initialize();
    }
}
