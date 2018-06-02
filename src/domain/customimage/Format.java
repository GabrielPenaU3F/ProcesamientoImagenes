package domain.customimage;

import java.util.ArrayList;
import java.util.List;

public class Format {

    public static final String RAW = "raw";
    public static final String PPM = "ppm";
    public static final String PGM = "pgm";
    public static final String BMP = "bmp";
    public static final String JPG = "jpg";
    public static final String JPEG = "jpeg";
    public static final String PNG = "png";

    private String formatString;
    private static final List<String> validFormats = validFormats();

    private static ArrayList<String> validFormats() {
        ArrayList<String> list = new ArrayList<>();
        list.add(RAW);
        list.add(PPM);
        list.add(PGM);
        list.add(BMP);
        list.add(JPG);
        list.add(JPEG);
        list.add(PNG);
        return list;
    }

    public Format(String format) {
        String formatAsLowerCase = format.toLowerCase();
        if(!isValid(formatAsLowerCase)) {
            throw new RuntimeException("Unsupported format");
        }
        this.formatString = formatAsLowerCase;
    }

    public static boolean isValid(String lowerFormat) {
        return validFormats.contains(lowerFormat);
    }

    public String getFormatString() {
        return this.formatString;
    }

}
