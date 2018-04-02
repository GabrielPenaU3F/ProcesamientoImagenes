package presentation.presenter;

import core.action.image.LoadImageAction;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImagesSumPresenter {

    private final LoadImageAction loadImageAction;
    private CustomImage image1;
    private CustomImage image2;

    public ImagesSumPresenter(LoadImageAction loadImageAction) {
        this.loadImageAction = loadImageAction;
    }

    public void onloadImage1() {
        this.image1 = this.loadImageAction.execute();
        //System.out.println("a");//para prueba
    }

    public void onloadImage2() {
        this.image2 = this.loadImageAction.execute();
        //System.out.println("b");//para prueba
    }

    public Image onMakeImagesSum() {
        int resultantImageHeight = 1;
        int resultantImageWidth = 1;
        int[][][] auxImage1R; //es para almacenar los valores de la imagen mas chica. Completo con 0 los espacios faltantes
        int[][][] auxImage2R; //es para almacenar los valores de la imagen mas chica. Completo con 0 los espacios faltantes
        if (this.image1.getHeight() > this.image2.getHeight()) {
            resultantImageHeight = this.image1.getHeight();
        } else if (this.image2.getHeight() > this.image1.getHeight()) {
            resultantImageHeight = this.image2.getHeight();
        }
        if (this.image1.getWidth() > this.image2.getWidth()) {
            resultantImageWidth = this.image1.getWidth();
        } else if (this.image2.getWidth() > this.image1.getWidth()) {
            resultantImageWidth = this.image2.getWidth();
        }
        auxImage1R = new int[resultantImageWidth][resultantImageHeight][3];
        auxImage2R = new int[resultantImageWidth][resultantImageHeight][3];
        this.completeWithZero(auxImage1R);
        this.completeWithZero(auxImage2R);
        this.addImageToMatrix(auxImage1R,this.image1);
        this.addImageToMatrix(auxImage2R,this.image2);
        int[][][] resultantMatrix = this.sumMatrix(auxImage1R, auxImage2R, resultantImageWidth, resultantImageHeight);
        int resultantRedImageR = this.calculateR(resultantMatrix, 0);//Red
        int resultantGreenImageR = this.calculateR(resultantMatrix, 1);//Green
        int resultantBlueImageR = this.calculateR(resultantMatrix, 2);//Blue
        WritableImage resultantImage = new WritableImage(resultantImageWidth, resultantImageHeight);
        this.writeNewPixelsValuesInImage(resultantMatrix, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
        //System.out.println("c");//para prueba
    }

    private void completeWithZero(int[][][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                for (int k = 0; k < matrix[i][j].length; k++){
                    matrix[i][j][k] = 0;
                }
            }
        }
    }

    private void addImageToMatrix(int[][][] matrix, CustomImage image){
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                for (int k = 0; k < matrix[i][j].length; k++){
                    if(k == 0){
                        matrix[i][j][k] += image.getRChannelValue(i,j);
                    }
                    else if(k == 1){
                        matrix[i][j][k] += image.getGChannelValue(i,j);
                    }
                    else if(k == 2){
                        matrix[i][j][k] += image.getBChannelValue(i,j);
                    }
                }

                //System.out.println(image.getPixelValue(i,j));
                //System.out.println(matrix[i][j]);
            }
        }
    }

    private int[][][] sumMatrix(int[][][] matrix1, int[][][] matrix2, int width, int height){ //ambas matrices tienen el mismo tamanio
        int[][][] resultant_matrix = new int[width][height][3];
        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[i].length; j++){
                for (int k = 0; k < matrix1[i][j].length; k++){
                    resultant_matrix[i][j][k] = matrix1[i][j][k] + matrix2[i][j][k];
                }
            }
        }
        return resultant_matrix;
    }

    private int calculateR(int[][][] matrix, int channel){
        int actualR = 0;
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                if(matrix[i][j][channel] > actualR){
                    actualR = matrix[i][j][channel];
                }
            }
        }
        return actualR;
    }

    private void writeNewPixelsValuesInImage(int[][][] matrix, int imageRedR, int imageGreenR, int imageBlueR, WritableImage image){
        PixelWriter pixelWriter = image.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader(); //solo para prueba
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                int redPixelValue = 0;
                int greenPixelValue = 0;
                int bluePixelValue = 0;
                for (int k = 0; k < matrix[i][j].length; k++){
                    if(k == 0){
                        double result = matrix[i][j][k] * ((double) 255/imageRedR);
                        redPixelValue = (int) result;
                    }
                    else if(k == 1){
                        double result = matrix[i][j][k] * ((double) 255/imageGreenR);
                        greenPixelValue = (int) result;
                    }
                    else if(k == 2){
                        double result = matrix[i][j][k] * ((double) 255/imageBlueR);
                        bluePixelValue = (int) result;
                    }

                }
                Color color = Color.rgb(redPixelValue,greenPixelValue,bluePixelValue);
                pixelWriter.setColor(i,j,color);

                //pixelWriter.setArgb(i,j,newPixelValue);
                //System.out.println(matrix[i][j]);
               // System.out.println(imageR);
                //pixelWriter.setArgb(i,j,matrix[i][j] * (255/imageR));
                //System.out.println(pixelReader.getArgb(i,j));
            }
        }
    }

}

