package core.action.image;

import core.service.statistics.GrayLevelStatisticsService;
import domain.automaticthreshold.ImageLimitValues;
import domain.customimage.CustomImage;


public class GetImageLimitValuesAction {

    private GrayLevelStatisticsService grayLevelStatisticsService;

    public GetImageLimitValuesAction(GrayLevelStatisticsService grayLevelStatisticsService){
        this.grayLevelStatisticsService = grayLevelStatisticsService;
    }

    public ImageLimitValues execute(CustomImage customImage){
        int redChannelMin = this.grayLevelStatisticsService.calculateMinGrayLevel(customImage.getRedMatrix());
        int redChannelMax = this.grayLevelStatisticsService.calculateMaxGrayLevel(customImage.getRedMatrix());

        int greenChannelMin = this.grayLevelStatisticsService.calculateMinGrayLevel(customImage.getGreenMatrix());
        int greenChannelMax = this.grayLevelStatisticsService.calculateMaxGrayLevel(customImage.getGreenMatrix());

        int blueChannelMin = this.grayLevelStatisticsService.calculateMinGrayLevel(customImage.getBlueMatrix());
        int blueChannelMax = this.grayLevelStatisticsService.calculateMaxGrayLevel(customImage.getBlueMatrix());

        int[] min = {redChannelMin, greenChannelMin, blueChannelMin};
        int[] max = {redChannelMax, greenChannelMax, blueChannelMax};

        int finalMin = 255;
        int finalMax = 0;

        for (int i = 0; i < 3; i++){
            if (max[i] >= finalMax){
                finalMax = max[i];
            }

            if (min[i] <= finalMin){
                finalMin = min[i];
            }
        }

        ImageLimitValues imageLimitValues = new ImageLimitValues(finalMin, finalMax);

        return imageLimitValues;
    }
}
