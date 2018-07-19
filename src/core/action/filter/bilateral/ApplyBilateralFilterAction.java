package core.action.filter.bilateral;

import java.util.ArrayList;
import java.util.List;

import domain.customimage.CustomImage;
import domain.customimage.Format;
import domain.customimage.channel_matrix.LABChannelMatrix;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import domain.mask.filter.BilateralMask;

public class ApplyBilateralFilterAction {

    public CustomImage execute(CustomImage image, double closenessSigma, double similaritySigma, int maskSize, CustomImage.SystemType imageSystemType) {
        List<double[][]> filteredChannels = new ArrayList<>();
        int size = maskSize; //2 * this.getMeanSigma(closenessSigma, similaritySigma) + 1;
        BilateralMask mask = new BilateralMask(size, closenessSigma, similaritySigma);

        for (double[][] channel : image.getChannelMatrix(imageSystemType).getChannels()) {
            filteredChannels.add(mask.applyBilateralMask(channel));
        }

        return createCustomImage(filteredChannels, imageSystemType);
    }

    private CustomImage createCustomImage(List<double[][]> filteredChannels, CustomImage.SystemType imageSystemType) {
        if (CustomImage.SystemType.LAB.equals(imageSystemType)) {
            return new CustomImage(new LABChannelMatrix(filteredChannels.get(0), filteredChannels.get(1), filteredChannels.get(2)), Format.PNG);
        }
        return new CustomImage(new RGBChannelMatrix(filteredChannels.get(0), filteredChannels.get(1), filteredChannels.get(2)), Format.PNG);
    }


}
