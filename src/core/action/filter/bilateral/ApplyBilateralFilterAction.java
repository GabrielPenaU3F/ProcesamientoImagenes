package core.action.filter.bilateral;

import core.service.transformations.FormatConversionService;
import domain.customimage.CustomImage;
import domain.customimage.CustomImage.SystemType;
import domain.customimage.Format;
import domain.customimage.channel_matrix.Channel;
import domain.customimage.channel_matrix.LABChannelMatrix;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import domain.mask.filter.BilateralMask;
import io.reactivex.Single;

public class ApplyBilateralFilterAction {

    private final FormatConversionService formatConversionService;

    public ApplyBilateralFilterAction(FormatConversionService formatConversionService) {
        this.formatConversionService = formatConversionService;
    }

    public CustomImage execute(CustomImage image, double closenessSigma, double similaritySigma, int maskSize, SystemType imageSystemType) {
        BilateralMask mask = new BilateralMask(maskSize, closenessSigma, similaritySigma);

        if (SystemType.LAB.equals(imageSystemType)) {
            return applyMaskToImageForLabSystemType(image, mask);
        }

        return applyMaskToImageForRGBSystemType(image, mask);
    }

    private CustomImage applyMaskToImageForLabSystemType(CustomImage image, BilateralMask mask) {
        long time = System.currentTimeMillis();
        RGBChannelMatrix rgbChannelMatrix = image.getRgbChannelMatrix();
        LABChannelMatrix labChannelMatrix = formatConversionService.RGBtoLAB(rgbChannelMatrix);

        this.log("RGBtoLAB", time);

        Single<Channel> LChannel = Single.fromCallable(labChannelMatrix::getLAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel L", time));

        Single<Channel> AChannel = Single.fromCallable(labChannelMatrix::getAAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel A", time));

        Single<Channel> BChannel = Single.fromCallable(labChannelMatrix::getBAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel B", time));

        return Single.zip(LChannel, AChannel, BChannel, (filteredLChannel, filteredAChannel, filteredBChannel) -> {
            double[][] filteredL = filteredLChannel.getChannel();
            double[][] filteredA = filteredAChannel.getChannel();
            double[][] filteredB = filteredBChannel.getChannel();
            RGBChannelMatrix rgbChannelMatrix1 = formatConversionService.LABtoRGB(new LABChannelMatrix(filteredL, filteredA, filteredB));
            this.log("Filtered RGBtoLAB", time);
            return new CustomImage(rgbChannelMatrix1, Format.PNG);
        }).blockingGet();
    }

    private CustomImage applyMaskToImageForRGBSystemType(CustomImage image, BilateralMask mask) {
        RGBChannelMatrix rgbChannelMatrix = image.getRgbChannelMatrix();
        long time = System.currentTimeMillis();

        Single<Channel> LChannel = Single.fromCallable(rgbChannelMatrix::getRedAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel R", time));

        Single<Channel> AChannel = Single.fromCallable(rgbChannelMatrix::getGreenAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel G", time));

        Single<Channel> BChannel = Single.fromCallable(rgbChannelMatrix::getBlueAsChannel)
                                         .map(mask::applyBilateralMask)
                                         .doOnSuccess(channel -> log("Filter Channel B", time));

        return Single.zip(LChannel, AChannel, BChannel, (filteredLChannel, filteredAChannel, filteredBChannel) -> {
            double[][] filteredL = filteredLChannel.getChannel();
            double[][] filteredA = filteredAChannel.getChannel();
            double[][] filteredB = filteredBChannel.getChannel();
            return new CustomImage(new RGBChannelMatrix(filteredL, filteredA, filteredB), Format.PNG);
        }).blockingGet();
    }

    private void log(String tag, long time) {
        System.out.println(tag + " in time: " + (System.currentTimeMillis() - time));
    }

}
