package core.action.filter.bilateral;

import domain.customimage.CustomImage;
import domain.customimage.channel_matrix.ChannelMatrix;
import domain.mask.Mask;
import domain.mask.filter.BilateralMask;

import java.util.ArrayList;
import java.util.List;

public class ApplyBilateralFilterAction {

    public CustomImage execute(CustomImage image, double closenessSigma, double similaritySigma) {
        return this.apply(image.getLABChannelMatrix(), closenessSigma, similaritySigma); //Here we can change LAB by RGB to test the algorithm on RGB System
    }

    public CustomImage apply(ChannelMatrix channels, double closenessSigma, double similaritySigma) {

        List<double[][]> filteredChannels = new ArrayList<>();

        for (double[][] channel : channels.getChannels()) {

            int size = 2*this.getMeanSigma(closenessSigma,similaritySigma)+1;
            BilateralMask mask = new BilateralMask(size, closenessSigma, similaritySigma);
            filteredChannels.add(mask.applyBilateralMask(channel));

        }
        //TODO: Crear una imagen a partir de los canales filtrados. De alguna manera habría que detectar en que sistema (RGB o LAB) viene la imagen para poder crearla.
        return null; //DEVOLVER ACA LA IMAGEN
    }

    private int getMeanSigma(double closenessSigma, double similaritySigma) {
        return (int)(closenessSigma + similaritySigma)/2;
    }

}
