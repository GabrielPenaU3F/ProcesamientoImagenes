package core.service.transformations;

import domain.CIELabConverter;
import domain.customimage.LAB;
import domain.customimage.RGB;
import domain.customimage.channel_matrix.LABChannelMatrix;
import domain.customimage.channel_matrix.RGBChannelMatrix;

public class FormatConversionService {

    public static RGBChannelMatrix LABtoRGB(LABChannelMatrix labChannelMatrix) {

        RGBChannelMatrix rgbChannelMatrix = new RGBChannelMatrix(labChannelMatrix.getWidth(), labChannelMatrix.getHeight());

        for (int i = 0; i < labChannelMatrix.getWidth(); i++) {
            for (int j = 0; j < labChannelMatrix.getHeight(); j++) {
                LAB lab = new LAB(labChannelMatrix.getLChannel()[i][j], labChannelMatrix.getAChannel()[i][j], labChannelMatrix.getBChannel()[i][j]);
                rgbChannelMatrix.setValue(i, j, CIELabConverter.LABtoRGB(lab));
            }
        }
        return rgbChannelMatrix;
    }

    public static LABChannelMatrix RGBtoLAB(RGBChannelMatrix rgbChannelMatrix) {

        LABChannelMatrix labChannelMatrix = new LABChannelMatrix(rgbChannelMatrix.getWidth(), rgbChannelMatrix.getHeight());

        for (int i = 0; i < rgbChannelMatrix.getWidth(); i++) {
            for (int j = 0; j < rgbChannelMatrix.getHeight(); j++) {
                RGB rgb = new RGB(rgbChannelMatrix.getRedChannel()[i][j], rgbChannelMatrix.getGreenChannel()[i][j], rgbChannelMatrix.getBlueChannel()[i][j]);
                labChannelMatrix.setValue(i, j, CIELabConverter.RGBtoLAB(rgb));
            }
        }
        return labChannelMatrix;
    }
}
