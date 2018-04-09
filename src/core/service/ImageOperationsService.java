package core.service;

import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageOperationsService {

    //completa la writableImage recibida, con el valor de cierta imagen (completando con 0 las posiciones en la cual la imagen no tiene valores)
    public WritableImage fillImage(WritableImage writableImage, CustomImage image) {
        this.completeWithZero(writableImage);
        this.setChannelsPixelsValuesInImage(writableImage,image);
        return writableImage;
    }

    public int calculateResultantWidth(CustomImage image1, CustomImage image2){
        int resultantImageWidth = 1;
        if (image1.getWidth() > image2.getWidth()) {
            resultantImageWidth = image1.getWidth();
        } else if (image2.getWidth() >= image1.getWidth()) {
            resultantImageWidth = image2.getWidth();
        }
        return resultantImageWidth;
    }

    public int calculateResultantHeight(CustomImage image1, CustomImage image2){
        int resultantImageHeight = 1;
        if (image1.getHeight() > image2.getHeight()) {
            resultantImageHeight = image1.getHeight();
        } else if (image2.getHeight() >= image1.getHeight()) {
            resultantImageHeight = image2.getHeight();
        }
        return resultantImageHeight;
    }

    public void completeWithZero(WritableImage imageToNormalize){
        PixelWriter pixelWriter = imageToNormalize.getPixelWriter();
        for (int i = 0; i < imageToNormalize.getWidth(); i++){
            for (int j = 0; j < imageToNormalize.getHeight(); j++){
                Color color = Color.rgb(0,0,0);
                pixelWriter.setColor(i,j,color);
            }
        }
    }

    public void setChannelsPixelsValuesInImage(WritableImage imageToNormalize, CustomImage image){
        int redChannelValue = 0;
        int greenChannelValue = 0;
        int blueChannelValue = 0;
        PixelWriter pixelWriter = imageToNormalize.getPixelWriter();
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                redChannelValue = image.getRChannelValue(i,j).intValue();
                greenChannelValue = image.getGChannelValue(i,j).intValue();
                blueChannelValue = image.getBChannelValue(i,j).intValue();
                Color color = Color.rgb(redChannelValue,greenChannelValue,blueChannelValue);
                pixelWriter.setColor(i,j,color);
            }
        }
    }

    //calcula el maximo valor existente
    public int calculateR(int[][] channelValues){
        int actualR = 0;
        for (int i = 0; i < channelValues.length; i++){
            for (int j = 0; j < channelValues[i].length; j++){
                if(channelValues[i][j] > actualR){
                    actualR = channelValues[i][j];
                }
            }
        }
        return actualR;
    }

    //calcula el minimo valor existente
    public int calculateMinValue(int[][] channelValues){
        int actualMin = 0;
        for (int i = 0; i < channelValues.length; i++){
            for (int j = 0; j < channelValues[i].length; j++){
                if(channelValues[i][j] < actualMin){
                    actualMin = channelValues[i][j];
                }
            }
        }
        return actualMin;
    }

    public Image writeNewPixelsValuesInImage(int[][] redChannelValues, int[][] greenChannelValues,
                                            int[][] blueChannelValues, int width, int height){
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        int redPixelValue = 0;
        int greenPixelValue = 0;
        int bluePixelValue = 0;
        int imageRedR = this.calculateR(redChannelValues);
        int imageGreenR = this.calculateR(greenChannelValues);
        int imageBlueR = this.calculateR(blueChannelValues);
        for (int i = 0; i < (int)image.getWidth(); i++){
            for (int j = 0; j < (int)image.getHeight(); j++){
                double auxRedPixelValue = redChannelValues[i][j] * ((double) 255/imageRedR);
                redPixelValue = (int) auxRedPixelValue;
                double auxGreenPixelValue = greenChannelValues[i][j] * ((double) 255/imageGreenR);
                greenPixelValue = (int) auxGreenPixelValue;
                double auxBluePixelValue = blueChannelValues[i][j] * ((double) 255/imageBlueR);
                bluePixelValue = (int) auxBluePixelValue;
                Color color = Color.rgb(redPixelValue,greenPixelValue,bluePixelValue);
                pixelWriter.setColor(i,j,color);
            }
        }
        return image;
    }

    public int[][] sumRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) + (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] sumGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) + (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] sumBluePixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) + (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) * (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) * (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyBluePixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) * (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyRedPixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber){
        int[][] result = new int[(int) customImage.getWidth()][(int) customImage.getHeight()];
        for(int i = 0; i < (int) customImage.getWidth(); i++) {
            for (int j = 0; j < (int) customImage.getHeight(); j++) {
                double sumResult = (customImage.getRChannelValue(i,j)) * (scalarNumber);
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyGreenPixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber){
        int[][] result = new int[(int) customImage.getWidth()][(int) customImage.getHeight()];
        for(int i = 0; i < (int) customImage.getWidth(); i++) {
            for (int j = 0; j < (int) customImage.getHeight(); j++) {
                double sumResult = (customImage.getGChannelValue(i,j)) * (scalarNumber);
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] multiplyBluePixelsValuesWithScalarNumber(CustomImage customImage, int scalarNumber){
        int[][] result = new int[(int) customImage.getWidth()][(int) customImage.getHeight()];
        for(int i = 0; i < (int) customImage.getWidth(); i++) {
            for (int j = 0; j < (int) customImage.getHeight(); j++) {
                double sumResult = (customImage.getBChannelValue(i,j)) * (scalarNumber);
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] substractRedPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getRed() * 255) - (pixelReaderImage2.getColor(i, j).getRed() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] substractGreenPixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getGreen() * 255) - (pixelReaderImage2.getColor(i, j).getGreen() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    public int[][] substractBluePixelsValues(Image image1, Image image2) {
        int[][] result = new int[(int) image1.getWidth()][(int) image1.getHeight()];
        PixelReader pixelReaderImage1 = image1.getPixelReader();
        PixelReader pixelReaderImage2 = image2.getPixelReader();
        for(int i = 0; i < (int) image1.getWidth(); i++) {
            for (int j = 0; j < (int) image1.getHeight(); j++) {
                double sumResult = ((pixelReaderImage1.getColor(i, j).getBlue() * 255) - (pixelReaderImage2.getColor(i, j).getBlue() * 255));
                result[i][j] = (int) Math.round(sumResult);
            }
        }
        return this.displacePixelsValues(result);
    }

    //desplazo los valores para que el minimo sea cero
    public int[][] displacePixelsValues(int[][] pixelsValues){
        int minPixelValue = this.calculateMinValue(pixelsValues);
        for (int i = 0; i < pixelsValues.length; i++){
            for (int j = 0; j < pixelsValues[i].length; j++){
                pixelsValues[i][j] -= minPixelValue;
            }
        }
        return pixelsValues;
    }

}
