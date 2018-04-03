package presentation.presenter;

import core.action.gradient.CreateImageWithGradientAction;
import core.action.histogram.CreateImageHistogramAction;
import core.action.image.GetImageAction;
import domain.Gradient;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import presentation.controller.ImageHistogramSceneController;

import java.awt.image.BufferedImage;

public class ImageHistogramPresenter {

    private final ImageHistogramSceneController view;
    private final GetImageAction getImageAction;
    private final CreateImageHistogramAction createImageHistogramAction;

    public ImageHistogramPresenter(ImageHistogramSceneController view,
                                   GetImageAction getImageAction,
                                   CreateImageHistogramAction createImageHistogramAction) {

        this.view = view;
        this.getImageAction = getImageAction;
        this.createImageHistogramAction = createImageHistogramAction;
    }

    public void initialize() {
        this.getImageAction.execute()
                .ifPresent(customImage -> this.setData(this.createImageHistogramAction.execute(customImage)));
    }

    private void setData(double[] value) {
        for (int i = 0; i < value.length; i++) {
            view.data.getData().add(createNode(String.valueOf(i), value[i]));
        }
        view.barChart.getData().add(view.data);
    }

    private XYChart.Data createNode(String x, double y) {
        XYChart.Data chartData = new XYChart.Data(x, y);
        chartData.nodeProperty().addListener((ChangeListener<Node>) (ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: black;"));
        return chartData;
    }
}
