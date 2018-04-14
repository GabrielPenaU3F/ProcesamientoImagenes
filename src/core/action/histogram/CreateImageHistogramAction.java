package core.action.histogram;

import core.service.generation.HistogramService;
import domain.Histogram;
import domain.customimage.CustomImage;

public class CreateImageHistogramAction {

    private HistogramService histogramService;

    public CreateImageHistogramAction(HistogramService histogramService) {
        this.histogramService = histogramService;
    }

    public Histogram execute(CustomImage customImage) {
        return histogramService.create(customImage);
    }
}
