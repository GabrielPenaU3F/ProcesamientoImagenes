package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import presentation.presenter.space_domain_operations.ImageHistogramPresenter;

public class ImageHistogramSceneController {

    @FXML
    public BarChart barChart;
    public XYChart.Series data;

    private final ImageHistogramPresenter imageHistogramPresenter;

    public ImageHistogramSceneController() {
        this.imageHistogramPresenter = PresenterProvider.provideImageHistogramPresenter(this);
    }

    @FXML
    public void initialize() {
        data = new XYChart.Series();
        imageHistogramPresenter.initialize();
    }
}
