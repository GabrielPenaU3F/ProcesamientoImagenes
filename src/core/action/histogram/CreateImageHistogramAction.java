package core.action.histogram;

import core.service.HistogramService;
import domain.customimage.CustomImage;

public class CreateImageHistogramAction {

    private HistogramService histogramService;

    public CreateImageHistogramAction(HistogramService histogramService) {
        this.histogramService = histogramService;
    }

    public double[] execute(CustomImage customImage) {
        return histogramService.create(customImage);
    }
}
