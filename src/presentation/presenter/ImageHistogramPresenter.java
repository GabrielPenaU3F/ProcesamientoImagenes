package presentation.presenter;

import core.action.gradient.CreateImageWithGradientAction;
import core.action.gradient.semaphore.GradientSemaphore;
import core.action.histogram.CreateImageHistogramAction;
import core.action.image.GetImageAction;
import domain.Gradient;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import presentation.controller.ImageHistogramSceneController;

public class ImageHistogramPresenter {

    private final ImageHistogramSceneController view;
    private final GetImageAction getImageAction;
    private final CreateImageHistogramAction createImageHistogramAction;
    private CreateImageWithGradientAction createImageWithGradient;

    public ImageHistogramPresenter(ImageHistogramSceneController view,
                                   GetImageAction getImageAction,
                                   CreateImageHistogramAction createImageHistogramAction,
                                   CreateImageWithGradientAction createImageWithGradient) {

        this.view = view;
        this.getImageAction = getImageAction;
        this.createImageHistogramAction = createImageHistogramAction;
        this.createImageWithGradient = createImageWithGradient;
    }

    public void initialize() {
        this.getImageAction.execute()
                .ifPresent(customImage -> this.setData(this.createImageHistogramAction.execute(customImage)));
        this.view.imageView.setImage(createImageWithGrayGradient());
    }

    private void setData(double[] value) {
        for (int i = 0; i < value.length; i++) {
            addColumn(String.valueOf(i), value[i]);
        }
        view.barChart.getData().add(view.data);
    }

    private void addColumn(String x, double y) {
        view.data.getData().add(new XYChart.Data(x, y));
    }

    private Image createImageWithGrayGradient() {
        GradientSemaphore.setValue(Gradient.GREY);
        return createImageWithGradient.execute((int) view.imageView.getFitWidth(), (int) view.imageView.getFitHeight());
    }
}
