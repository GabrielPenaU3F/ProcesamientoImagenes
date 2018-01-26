package domain;

public class Format {

    private String formatString;

    public Format(String format) {

        String lowerFormat = format.toLowerCase();

        switch(lowerFormat) {

            case "raw":
                this.formatString = lowerFormat;
                break;
            case "ppm":
                this.formatString = lowerFormat;
                break;
            case "pgm":
                this.formatString = lowerFormat;
                break;
            case "bmp":
                this.formatString = lowerFormat;
                break;
            case "jpg":
                this.formatString = lowerFormat;
                break;
            case "png":
                this.formatString = lowerFormat;
                break;
            default:
                throw new RuntimeException ("Unsupported format");


        }

    }

    public String getFormatString() {
        return this.formatString;
    }

}
