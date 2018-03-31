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
        int[][] auxImage1; //es para almacenar los valores de la imagen mas chica. Completo con 0 los espacios faltantes
        int[][] auxImage2; //es para almacenar los valores de la imagen mas chica. Completo con 0 los espacios faltantes
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
        auxImage1 = new int[resultantImageWidth][resultantImageHeight];
        auxImage2 = new int[resultantImageWidth][resultantImageHeight];
        this.completeWithZero(auxImage1);
        this.completeWithZero(auxImage2);
        this.addImageToMatrix(auxImage1,this.image1);
        this.addImageToMatrix(auxImage2,this.image2);
        int[][] resultantMatrix = this.sumMatrix(auxImage1, auxImage2, resultantImageWidth, resultantImageHeight);
        int resultantImageR = this.calculateR(resultantMatrix);
        WritableImage resultantImage = new WritableImage(resultantImageWidth, resultantImageHeight);
        this.writeNewPixelsValuesInImage(resultantMatrix, resultantImageR, resultantImage);
        return resultantImage;
        //System.out.println("c");//para prueba
    }

    private void completeWithZero(int[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = 0;
            }
        }
    }

    private void addImageToMatrix(int[][] matrix, CustomImage image){
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                matrix[i][j] += image.getPixelValue(i, j);
                //System.out.println(image.getPixelValue(i,j));
                //System.out.println(matrix[i][j]);
            }
        }
    }

    private int[][] sumMatrix(int[][] matrix1, int[][] matrix2, int width, int height){ //ambas matrices tienen el mismo tamanio
        int[][] resultant_matrix = new int[width][height];
        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[i].length; j++){
                resultant_matrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return resultant_matrix;
    }

    private int calculateR(int[][] matrix){
        int actualR = 0;
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                if(matrix[i][j] > actualR){
                    actualR = matrix[i][j];
                }
            }
        }
        return actualR;
    }

    private void writeNewPixelsValuesInImage(int[][] matrix, int imageR, WritableImage image){
        PixelWriter pixelWriter = image.getPixelWriter();
        PixelReader pixelReader = image.getPixelReader(); //solo para prueba
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                double result = matrix[i][j] * ((double) 255/imageR);
                int newPixelValue = (int) result;
                Color color = Color.rgb(newPixelValue,newPixelValue,newPixelValue);
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

