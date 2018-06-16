package core.service;

import core.service.statistics.GrayLevelStatisticsService;
import domain.customimage.ChannelMatrix;
import domain.customimage.CustomImage;
import domain.customimage.Pixel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.List;

public class ImageOperationsService {

    private GrayLevelStatisticsService grayLevelStatisticsService;

    public ImageOperationsService(GrayLevelStatisticsService grayLevelStatisticsService) {
        this.grayLevelStatisticsService = grayLevelStatisticsService;
    }

    //completa la writableImage recibida, con el valor de cierta imagen (completando con 0 las posiciones en la cual la imagen no tiene valores)
    public WritableImage fillImage(WritableImage writableImage, CustomImage image) {
        this.completeWithZero(writableImage);
        this.setChannelsPixelsValuesInImage(writableImage, image);
        return writableImage;
    }

    public int calculateResultantWidth(CustomImage image1, CustomImage image2) {
        int resultantImageWidth = 1;
        if (image1.getWidth() > image2.getWidth()) {
            resultantImageWidth = image1.getWidth();
        } else if (image2.getWidth() >= image1.getWidth()) {
            resultantImageWidth = image2.getWidth();
        }
        return resultantImageWidth;
    }

    public int calculateResultantHeight(CustomImage image1, CustomImage image2) {
        int resultantImageHeight = 1;
        if (image1.getHeight() > image2.getHeight()) {
            resultantImageHeight = image1.getHeight();
        } else if (image2.getHeight() >= image1.getHeight()) {
            resultantImageHeight = image2.getHeight();
        }
        return resultantImageHeight;
    }

    public void completeWithZero(WritableImage imageToNormalize) {
        PixelWriter pixelWriter = imageToNormalize.getPixelWriter();
        for (int i = 0; i < imageToNormalize.getWidth(); i++) {
            for (int j = 0; j < imageToNormalize.getHeight(); j++) {
                Color color = Color.rgb(0, 0, 0);
                pixelWriter.setColor(i, j, color);
            }
        }
    }

    public void completeWithZero(int[][] image) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                image[i][j] = 0;
            }
        }
    }

    private void setChannelsPixelsValuesInImage(WritableImage imageToNormalize, CustomImage image) {
        int redChannelValue = 0;
        int greenChannelValue = 0;
        int blueChannelValue = 0;
        PixelWriter pixelWriter = imageToNormalize.getPixelWriter();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                redChannelValue = image.getRChannelValue(i, j);
                greenChannelValue = image.getGChannelValue(i, j);
                blueChannelValue = image.getBChannelValue(i, j);
                Color color = Color.rgb(redChannelValue, greenChannelValue, blueChannelValue);
                pixelWriter.setColor(i, j, color);
            }
        }
    }

    public int[][] adjustScale(int[][] channelValues) {
        int imageR = this.grayLevelStatisticsService.calculateMaxGrayLevel(channelValues);
        for (int i = 0; i < channelValues.length; i++) {
            for (int j = 0; j < channelValues[i].length; j++) {
                int newPixelValue = (int) (channelValues[i][j] * ((double) 255 / imageR));
                channelValues[i][j] = newPixelValue;
            }
        }
        return channelValues;
    }

    public int[][] adjustScale(int[][] channelValues, List<Pixel> contaminatedPixels) {
        int imageR = this.grayLevelStatisticsService.calculateMaxGrayLevel(channelValues);
        for (Pixel pixel : contaminatedPixels) {
            int oldPixelValue = channelValues[pixel.getX()][pixel.getY()];
            int newPixelValue = (int) (oldPixelValue * ((double) 255 / imageR));
            channelValues[pixel.getX()][pixel.getY()] = newPixelValue;
        }
        return channelValues;
    }

    public int[][] toValidContaminatedImage(int[][] channelValues, List<Pixel> contaminatedPixels) {
        return adjustScale(displacePixelsValues(channelValues), contaminatedPixels);
    }

    public Image writeNewPixelsValuesToImage(int[][] redChannelValues, int[][] greenChannelValues,
                                             int[][] blueChannelValues) {
        int width = redChannelValues.length;
        int height = redChannelValues[0].length;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        int redPixelValue = 0;
        int greenPixelValue = 0;
        int bluePixelValue = 0;
        for (int i = 0; i < (int) image.getWidth(); i++) {
            for (int j = 0; j < (int) image.getHeight(); j++) {
                redPixelValue = redChannelValues[i][j];
                greenPixelValue = greenChannelValues[i][j];
                bluePixelValue = blueChannelValues[i][j];
                Color color = Color.rgb(redPixelValue, greenPixelValue, bluePixelValue);
                pixelWriter.setColor(i, j, color);
            }
        }
        return image;
    }

    public int[][] sumRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) + (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] sumGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) + (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] sumBluePixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) + (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] multiplyRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double productResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) * (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] multiplyGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double productResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) * (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] multiplyGrayPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double productResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) * (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] multiplyRedPixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber) {
        int[][] result = new int[(customImage.getWidth())][customImage.getHeight()];
        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                double productResult = customImage.getRChannelValue(i, j) * scalarNumber;
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return result;
    }

    public int[][] multiplyGreenPixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber) {
        int[][] result = new int[(customImage.getWidth())][customImage.getHeight()];
        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                double productResult = customImage.getGChannelValue(i, j) * scalarNumber;
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return result;
    }

    public int[][] multiplyBluePixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber) {
        int[][] result = new int[customImage.getWidth()][customImage.getHeight()];
        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                double productResult = customImage.getBChannelValue(i, j) * scalarNumber;
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return result;
    }

    public int[][] substractRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) - (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] substractGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) - (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    public int[][] substractBluePixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for (int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) - (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.adjustScale(this.displacePixelsValues(result));
    }

    //desplazo los valores para que el minimo sea cero
    public int[][] displacePixelsValues(int[][] pixelsValues) {
        int minPixelValue = this.grayLevelStatisticsService.calculateMinGrayLevel(pixelsValues);
        for (int i = 0; i < pixelsValues.length; i++) {
            for (int j = 0; j < pixelsValues[i].length; j++) {
                pixelsValues[i][j] -= minPixelValue;
            }
        }
        return pixelsValues;
    }

    private int[][] toValidImageMatrix(int[][] pixels) {
        return this.adjustScale(this.displacePixelsValues(pixels));
    }

    public ChannelMatrix toValidImageMatrix(ChannelMatrix channelMatrix) {
        int[][] redChannel = this.toValidImageMatrix(channelMatrix.getRedChannel());
        int[][] greenChannel = this.toValidImageMatrix(channelMatrix.getGreenChannel());
        int[][] blueChannel = this.toValidImageMatrix(channelMatrix.getBlueChannel());
        return new ChannelMatrix(redChannel, greenChannel, blueChannel);
    }

    public ChannelMatrix multiplyChannelMatrixs(ChannelMatrix channelMatrix1, ChannelMatrix channelMatrix2) {
        int[][] redChannel = multiplyMatrix(channelMatrix1.getRedChannel(), channelMatrix2.getRedChannel());
        int[][] greenChannel = multiplyMatrix(channelMatrix1.getGreenChannel(), channelMatrix2.getGreenChannel());
        int[][] blueChannel = multiplyMatrix(channelMatrix1.getBlueChannel(), channelMatrix2.getBlueChannel());
        return toValidImageMatrix(new ChannelMatrix(redChannel, greenChannel, blueChannel));
    }

    public ChannelMatrix sumChannelMatrixs(ChannelMatrix channelMatrix1, ChannelMatrix channelMatrix2) {
        int[][] redChannel = sumMatrix(channelMatrix1.getRedChannel(), channelMatrix2.getRedChannel());
        int[][] greenChannel = sumMatrix(channelMatrix1.getGreenChannel(), channelMatrix2.getGreenChannel());
        int[][] blueChannel = sumMatrix(channelMatrix1.getBlueChannel(), channelMatrix2.getBlueChannel());
        return toValidImageMatrix(new ChannelMatrix(redChannel, greenChannel, blueChannel));
    }

    public ChannelMatrix sqrtChannelMatrixs(ChannelMatrix channelMatrix) {
        int[][] redChannel = sqrtMatrix(channelMatrix.getRedChannel());
        int[][] greenChannel = sqrtMatrix(channelMatrix.getGreenChannel());
        int[][] blueChannel = sqrtMatrix(channelMatrix.getBlueChannel());
        return toValidImageMatrix(new ChannelMatrix(redChannel, greenChannel, blueChannel));
    }

    public int[][] sumMatrix(int[][] matrix1, int[][] matrix2) {
        int width = matrix1.length;
        int height = matrix1[0].length;
        int[][] result = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    public int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2) {
        int width = matrix1.length;
        int height = matrix1[0].length;
        int[][] result = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double productResult = matrix1[i][j] * matrix2[i][j];
                result[i][j] = (int) Math.round(productResult);
            }
        }
        return result;
    }

    private int[][] sqrtMatrix(int[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;

        int[][] result = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double value = Math.sqrt(matrix[i][j]);
                result[i][j] = (int) Math.round(value);
            }
        }
        return result;
    }

    public ChannelMatrix calculateAbsoluteSum(ChannelMatrix firstImage, ChannelMatrix secondImage) {
        int[][] redChannel = this.calculateAbsoluteSum(firstImage.getRedChannel(), secondImage.getRedChannel());
        int[][] greenChannel = this.calculateAbsoluteSum(firstImage.getGreenChannel(), secondImage.getGreenChannel());
        int[][] blueChannel = this.calculateAbsoluteSum(firstImage.getBlueChannel(), secondImage.getBlueChannel());
        return new ChannelMatrix(redChannel, greenChannel, blueChannel);
    }

    private int[][] calculateAbsoluteSum(int[][] firstChannel, int[][] secondChanne1) {

        int[][] absoluteSumChannel = new int[firstChannel.length][firstChannel[0].length];

        for (int x=0; x < firstChannel.length; x++) {
            for (int y=0; y < firstChannel[x].length; y++) {
                absoluteSumChannel[x][y] = Math.abs(firstChannel[x][y]) + Math.abs(secondChanne1[x][y]);
            }
        }

        return absoluteSumChannel;
    }

}
