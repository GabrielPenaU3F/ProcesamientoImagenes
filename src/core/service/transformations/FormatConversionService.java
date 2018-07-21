package core.service.transformations;

import java.util.ArrayList;
import java.util.List;

import domain.CIELabConverter;
import domain.customimage.LAB;
import domain.customimage.RGB;
import domain.customimage.channel_matrix.LABChannelMatrix;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import io.reactivex.Single;

public class FormatConversionService {

    private final CIELabConverter converter;

    public FormatConversionService(CIELabConverter converter) {
        this.converter = converter;
    }

    public RGBChannelMatrix LABtoRGB(LABChannelMatrix labChannelMatrix) {
        List<Single<RGB>> singles = new ArrayList<>();
        RGBChannelMatrix rgbChannelMatrix = new RGBChannelMatrix(labChannelMatrix.getWidth(), labChannelMatrix.getHeight());
        double[][] lChannel = labChannelMatrix.getLChannel();
        double[][] aChannel = labChannelMatrix.getAChannel();
        double[][] bChannel = labChannelMatrix.getBChannel();

        for (int i = 0; i < labChannelMatrix.getWidth(); i++) {
            for (int j = 0; j < labChannelMatrix.getHeight(); j++) {
                final int ic = i;
                final int jc = j;

                LAB lab = new LAB(lChannel[i][j], aChannel[i][j], bChannel[i][j]);
                Single<RGB> rgbSingle = Single.fromCallable(() -> this.converter.LABtoRGB(lab))
                                              .map(value -> rgbChannelMatrix.setValue(ic, jc, value));

                singles.add(rgbSingle);
            }
        }
        Single.merge(singles).toList().blockingGet();
        return rgbChannelMatrix;
    }

    public LABChannelMatrix RGBtoLAB(RGBChannelMatrix rgbChannelMatrix) {
        List<Single<LAB>> singles = new ArrayList<>();
        LABChannelMatrix labChannelMatrix = new LABChannelMatrix(rgbChannelMatrix.getWidth(), rgbChannelMatrix.getHeight());
        int[][] redChannel = rgbChannelMatrix.getRedChannel();
        int[][] greenChannel = rgbChannelMatrix.getGreenChannel();
        int[][] blueChannel = rgbChannelMatrix.getBlueChannel();

        for (int i = 0; i < rgbChannelMatrix.getWidth(); i++) {
            for (int j = 0; j < rgbChannelMatrix.getHeight(); j++) {
                final int ic = i;
                final int jc = j;

                RGB rgb = new RGB(redChannel[i][j], greenChannel[i][j], blueChannel[i][j]);
                Single<LAB> labSingle = Single.fromCallable(() -> this.converter.RGBtoLAB(rgb))
                                              .map(value -> labChannelMatrix.setValue(ic, jc, value));
                singles.add(labSingle);
            }
        }
        Single.merge(singles).toList().blockingGet();
        return labChannelMatrix;
    }
}
