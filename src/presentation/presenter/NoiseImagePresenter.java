package presentation.presenter;

import core.action.histogram.CreateImageHistogramAction;
import domain.Histogram;
import domain.customimage.CustomImage;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import presentation.controller.NoiseImageSceneController;

public class NoiseImagePresenter {

    private final NoiseImageSceneController view;
    private final Observable<Image> onNoiseImage;
    private final CreateImageHistogramAction createImageHistogramAction;


    public NoiseImagePresenter(NoiseImageSceneController noiseImageSceneController, PublishSubject<Image> imagePublishSubject, CreateImageHistogramAction createImageHistogramAction) {

        this.view = noiseImageSceneController;
        this.onNoiseImage = imagePublishSubject;
        this.createImageHistogramAction = createImageHistogramAction;
    }

    public void initialize() {
        awaitingForNewModifiedImages();
    }

    private void awaitingForNewModifiedImages() {
        onNoiseImage.subscribe(image -> {
            view.noiseImageView.setImage(image);
            Histogram histogram = createImageHistogramAction.execute(new CustomImage(SwingFXUtils.fromFXImage(image,null), "png"));
            this.setHistogramData(histogram.getValues());

        });
    }

    private void setHistogramData(double[] value) {
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
