package domain;

import domain.customimage.LAB;
import domain.customimage.RGB;
import domain.customimage.XYZ;

public class CIELabConverter {

    /**
     * reference white in XYZ coordinates
     */
    private final double[] D50 = { 96.4212, 100.0, 82.5188 };
    private final double[] D55 = { 95.6797, 100.0, 92.1481 };
    private final double[] D65 = { 95.0429, 100.0, 108.8900 };
    private final double[] D75 = { 94.9722, 100.0, 122.6394 };
    private final double[] whitePoint = D65;

    /**
     * sRGB to XYZ conversion matrix
     */
    private final double[][] M = { { 0.4124, 0.3576, 0.1805 },
            { 0.2126, 0.7152, 0.0722 },
            { 0.0193, 0.1192, 0.9505 } };

    /**
     * XYZ to sRGB conversion matrix
     */
    private final double[][] Mi = { { 3.2406, -1.5372, -0.4986 },
            { -0.9689, 1.8758, 0.0415 },
            { 0.0557, -0.2040, 1.0570 } };

    /* Uncomment when need change the level of white
    public CIELabConverter(String white) {
        whitePoint = D65;
        if (white.equalsIgnoreCase("d50")) {
            whitePoint = D50;
        } else if (white.equalsIgnoreCase("d55")) {
            whitePoint = D55;
        } else if (white.equalsIgnoreCase("d65")) {
            whitePoint = D65;
        } else if (white.equalsIgnoreCase("d75")) {
            whitePoint = D75;
        }
    }*/

    public RGB LABtoRGB(LAB lab) {
        return XYZtoRGB(LABtoXYZ(lab));
    }

    public LAB RGBtoLAB(RGB rgb) {
        return XYZtoLAB(RGBtoXYZ(rgb));
    }

    private XYZ LABtoXYZ(LAB lab) {

        double y = (lab.getL() + 16.0) / 116.0;
        double y3 = Math.pow(y, 3.0);

        double x = (lab.getA() / 500.0) + y;
        double x3 = Math.pow(x, 3.0);

        double z = y - (lab.getB() / 200.0);
        double z3 = Math.pow(z, 3.0);

        if (y3 > 0.008856) {
            y = y3;
        } else {
            y = (y - (16.0 / 116.0)) / 7.787;
        }
        if (x3 > 0.008856) {
            x = x3;
        } else {
            x = (x - (16.0 / 116.0)) / 7.787;
        }
        if (z3 > 0.008856) {
            z = z3;
        } else {
            z = (z - (16.0 / 116.0)) / 7.787;
        }

        return new XYZ(x * whitePoint[0], y * whitePoint[1], z * whitePoint[2]);
    }

    private XYZ RGBtoXYZ(RGB rgb) {

        // convert 0..255 into 0..1
        double r = rgb.getRed() / 255.0;
        double g = rgb.getGreen() / 255.0;
        double b = rgb.getBlue() / 255.0;

        // assume sRGB
        if (r <= 0.04045) {
            r = r / 12.92 * 100.0;
        } else {
            r = Math.pow(((r + 0.055) / 1.055), 2.4) * 100.0;
        }
        if (g <= 0.04045) {
            g = g / 12.92 * 100.0;
        } else {
            g = Math.pow(((g + 0.055) / 1.055), 2.4) * 100.0;
        }
        if (b <= 0.04045) {
            b = b / 12.92 * 100.0;
        } else {
            b = Math.pow(((b + 0.055) / 1.055), 2.4) * 100.0;
        }

        /*se lo meto en las cuentas de arriba
        r *= 100.0;
        g *= 100.0;
        b *= 100.0;*/

        // [X Y Z] = [r g b][M]
        double x = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
        double y = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
        double z = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

        return new XYZ(x, y, z);
    }

    private LAB XYZtoLAB(XYZ xyz) {

        double x = xyz.getX() / whitePoint[0];
        double y = xyz.getY() / whitePoint[1];
        double z = xyz.getZ() / whitePoint[2];

        if (x > 0.008856) {
            x = Math.pow(x, 1.0 / 3.0);
        } else {
            x = (7.787 * x) + (16.0 / 116.0);
        }
        if (y > 0.008856) {
            y = Math.pow(y, 1.0 / 3.0);
        } else {
            y = (7.787 * y) + (16.0 / 116.0);
        }
        if (z > 0.008856) {
            z = Math.pow(z, 1.0 / 3.0);
        } else {
            z = (7.787 * z) + (16.0 / 116.0);
        }

        double L = (116.0 * y) - 16.0;
        double A = 500.0 * (x - y);
        double B = 200.0 * (y - z);

        return new LAB(L, A, B);
    }

    private RGB XYZtoRGB(XYZ xyz) {

        double x = xyz.getX() / 100.0;
        double y = xyz.getY() / 100.0;
        double z = xyz.getZ() / 100.0;

        // [r g b] = [X Y Z][Mi]
        double r = (x * Mi[0][0]) + (y * Mi[0][1]) + (z * Mi[0][2]);
        double g = (x * Mi[1][0]) + (y * Mi[1][1]) + (z * Mi[1][2]);
        double b = (x * Mi[2][0]) + (y * Mi[2][1]) + (z * Mi[2][2]);

        // assume sRGB
        if (r > 0.0031308) {
            r = ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055);
        } else {
            r = (r * 12.92);
        }
        if (g > 0.0031308) {
            g = ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055);
        } else {
            g = (g * 12.92);
        }
        if (b > 0.0031308) {
            b = ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055);
        } else {
            b = (b * 12.92);
        }

        int rAsInt = (r < 0) ? 0 : (int) Math.round(r * 255);
        int gAsInt = (g < 0) ? 0 : (int) Math.round(g * 255);
        int bAsInt = (b < 0) ? 0 : (int) Math.round(b * 255);

        return new RGB(rAsInt, gAsInt, bAsInt);
    }
}
