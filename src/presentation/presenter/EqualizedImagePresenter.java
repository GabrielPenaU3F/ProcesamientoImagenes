package presentation.presenter;

import core.action.histogram.CreateImageHistogramAction;
import core.action.histogram.EqualizeGrayImageAction;
import core.action.histogram.utils.EqualizedTimes;
import core.action.image.GetImageAction;
import domain.Histogram;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import presentation.controller.EqualizedImageSceneController;

public class EqualizedImagePresenter {

    private final EqualizedImageSceneController view;
    private final EqualizeGrayImageAction equalizeGrayImageAction;
    private final PublishSubject<Image> imagePublishSubject;
    private final GetImageAction getImageAction;
    private final CreateImageHistogramAction createImageHistogramAction;

    public EqualizedImagePresenter(EqualizedImageSceneController view,
                                   GetImageAction getImageAction,
                                   EqualizeGrayImageAction equalizeGrayImageAction,
                                   CreateImageHistogramAction createImageHistogramAction,
                                   PublishSubject<Image> imagePublishSubject) {
        this.view = view;
        this.getImageAction = getImageAction;
        this.equalizeGrayImageAction = equalizeGrayImageAction;
        this.createImageHistogramAction = createImageHistogramAction;
        this.imagePublishSubject = imagePublishSubject;
    }

    public void onInitializeView() {

        imagePublishSubject.subscribe(image -> view.equalizedImageView.setImage(image));

        getImageAction.execute().ifPresent(customImage -> {
            Image image = equalizeGrayImageAction.execute(customImage, EqualizedTimes.getValue());
            Histogram histogram = createImageHistogramAction.execute(new CustomImage(image, "png"));
            setHistogramData(histogram.getValues());
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
        ChangeListener<Node> nodeChangeListener = (ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: black;");
        chartData.nodeProperty().addListener(nodeChangeListener);
        return chartData;
    }

}
