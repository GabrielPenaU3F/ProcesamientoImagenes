package core.service.generation;

import domain.Histogram;
import domain.customimage.CustomImage;

import java.util.Arrays;

public class HistogramService {

    public Histogram create(CustomImage customImage) {

        Integer total = 0;
        double[] values = new double[257];

        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                values[customImage.getAverageValue(i, j)] += 1;
                total++;
            }
        }

        Double minValue = Arrays.stream(values).min().orElse(0.0);

        return new Histogram(values, total, minValue);
    }
}
