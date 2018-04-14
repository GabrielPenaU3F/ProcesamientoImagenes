package presentation.presenter.space_domain_operations;

import core.action.histogram.CreateImageHistogramAction;
import core.action.image.GetImageAction;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import presentation.controller.ImageHistogramSceneController;

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
                .ifPresent(customImage -> this.setData(this.createImageHistogramAction.execute(customImage).getValues()));
    }

    private void setData(double[] value) {
        for (int i = 0; i < value.length; i++) {
            view.data.getData().add(createNode(String.valueOf(i), value[i]));
        }
        view.barChart.getData().add(view.data);
    }

    private XYChart.Data createNode(String x, Double y) {
        XYChart.Data chartData = new XYChart.Data(x, y);
        chartData.nodeProperty().addListener((ChangeListener<Node>) (ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: black;"));
        return chartData;
    }
}
