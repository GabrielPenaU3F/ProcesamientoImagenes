package domain;

import java.util.ArrayList;
import java.util.List;

public class Format {

    private String formatString;
    private static List<String> validFormats = validFormats();

    private static ArrayList<String> validFormats() {
        ArrayList<String> list = new ArrayList<>();
        list.add("raw");
        list.add("ppm");
        list.add("pgm");
        list.add("bmp");
        list.add("jpg");
        list.add("png");
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
