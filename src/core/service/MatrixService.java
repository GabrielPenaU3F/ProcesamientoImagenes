package core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import domain.customimage.RGB;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MatrixService {

    public int[][] toChannelMatrix(BiFunction<Integer, Integer, Double> channel, int width, int height) {
        int[][] matrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = (int) (channel.apply(x, y) * 255);
            }
        }
        return matrix;
    }

    //Only for gray images
    public int[][] toGrayMatrix(Image image) {
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] matrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int grayMean = (int) (reader.getColor(x, y).getRed() * 255 + reader.getColor(x, y).getBlue() * 255 + reader.getColor(x, y).getGreen() * 255) / 3;
                matrix[x][y] = grayMean;
            }
        }
        return matrix;
    }

    public Image toGrayImage(int[][] matrix) {
        return toImage(matrix, matrix, matrix);
    }

    public Image toImage(int[][] red, int[][] green, int[][] blue) {
        int width = red.length;
        int height = red[0].length;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.rgb(red[i][j], green[i][j], blue[i][j]);
                pixelWriter.setColor(i, j, color);
            }
        }

        return image;
    }

    public int[][] copy(int[][] matrix) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] copy = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                copy[i][j] = matrix[i][j];
            }
        }

        return copy;
    }

    //We will mostly have to refactor this methods to support color images, i.e., using a ChannelMatrix instead of int[][]

    public List<RGB> obtainNeighbors(int[][] matrix, int x, int y) {

        //If the pixel is in a corner
        if (x == 0 && y == 0) {
            return this.obtainTopleftNeighbors(matrix);
        }
        if (x == matrix.length - 1 && y == 0) {
            return this.obtainToprightNeighbors(matrix);
        }
        if (x == 0 && y == matrix[0].length - 1) {
            return this.obtainBottomleftNeighbors(matrix);
        }
        if (x == matrix.length - 1 && y == matrix[0].length - 1) {
            return this.obtainBottomrightNeighbors(matrix);
        }

        //If the pixel is on a side, but not on a corner
        if (x == 0) {
            return this.obtainLeftSideNeighbors(matrix, y);
        }
        if (x == matrix.length - 1) {
            return this.obtainRightSideNeighbors(matrix, y);
        }
        if (y == 0) {
            return this.obtainTopSideNeighbors(matrix, x);
        }
        if (y == matrix[0].length - 1) {
            return this.obtainBottomSideNeighbors(matrix, x);
        }

        //If the pixel is anywhere else
        return this.obtainEveryNeighbors(matrix, x, y);

    }

    private List<RGB> obtainEveryNeighbors(int[][] matrix, int x, int y) {
        List<RGB> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            neighbors.add(new RGB(matrix[x + i][y - 1], matrix[x + i][y - 1], matrix[x + i][y - 1]));
            neighbors.add(new RGB(matrix[x + i][y + 1], matrix[x + i][y + 1], matrix[x + i][y + 1]));
        }
        neighbors.add(new RGB(matrix[x - 1][y], matrix[x - 1][y], matrix[x - 1][y]));
        neighbors.add(new RGB(matrix[x + 1][y], matrix[x + 1][y], matrix[x + 1][y]));
        return neighbors;
    }

    private List<RGB> obtainBottomSideNeighbors(int[][] matrix, int x) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[x - 1][matrix[x].length - 1], matrix[x - 1][matrix[x].length - 1], matrix[x - 1][matrix[x].length - 1]));
        neighbors.add(new RGB(matrix[x + 1][matrix[x].length - 1], matrix[x + 1][matrix[x].length - 1], matrix[x + 1][matrix[x].length - 1]));
        for (int i = -1; i <= 1; i++) {
            neighbors.add(new RGB(matrix[x + i][matrix[x].length - 2], matrix[x + i][matrix[x].length - 2], matrix[x + i][matrix[x].length - 2]));
        }
        return neighbors;
    }

    private List<RGB> obtainTopSideNeighbors(int[][] matrix, int x) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[x - 1][0], matrix[x - 1][0], matrix[x - 1][0]));
        neighbors.add(new RGB(matrix[x + 1][0], matrix[x + 1][0], matrix[x + 1][0]));
        for (int i = -1; i <= 1; i++) {
            neighbors.add(new RGB(matrix[x + i][1], matrix[x + i][1], matrix[x + i][1]));
        }
        return neighbors;
    }

    private List<RGB> obtainRightSideNeighbors(int[][] matrix, int y) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[matrix.length - 1][y - 1], matrix[matrix.length - 1][y - 1], matrix[matrix.length - 1][y - 1]));
        neighbors.add(new RGB(matrix[matrix.length - 1][y + 1], matrix[matrix.length - 1][y + 1], matrix[matrix.length - 1][y + 1]));
        for (int j = -1; j <= 1; j++) {
            neighbors.add(new RGB(matrix[matrix.length - 2][y + j], matrix[matrix.length - 2][y + j], matrix[matrix.length - 2][y + j]));
        }
        return neighbors;
    }

    private List<RGB> obtainLeftSideNeighbors(int[][] matrix, int y) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[0][y - 1], matrix[0][y - 1], matrix[0][y - 1]));
        neighbors.add(new RGB(matrix[0][y + 1], matrix[0][y + 1], matrix[0][y + 1]));
        for (int j = -1; j <= 1; j++) {
            neighbors.add(new RGB(matrix[1][y + j], matrix[1][y + j], matrix[1][y + j]));
        }
        return neighbors;
    }

    private List<RGB> obtainBottomrightNeighbors(int[][] matrix) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[matrix.length - 2][matrix[0].length - 1], matrix[matrix.length - 2][matrix[0].length - 1],
                matrix[matrix.length - 2][matrix[0].length - 1]));
        neighbors.add(new RGB(matrix[matrix.length - 1][matrix[0].length - 2], matrix[matrix.length - 1][matrix[0].length - 2],
                matrix[matrix.length - 1][matrix[0].length - 2]));
        neighbors.add(new RGB(matrix[matrix.length - 2][matrix[0].length - 2], matrix[matrix.length - 2][matrix[0].length - 2],
                matrix[matrix.length - 2][matrix[0].length - 2]));
        return neighbors;
    }

    private List<RGB> obtainBottomleftNeighbors(int[][] matrix) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[0][matrix[0].length - 2], matrix[0][matrix[0].length - 2], matrix[0][matrix[0].length - 2]));
        neighbors.add(new RGB(matrix[1][matrix[0].length - 1], matrix[1][matrix[0].length - 1], matrix[1][matrix[0].length - 1]));
        neighbors.add(new RGB(matrix[1][matrix[0].length - 2], matrix[1][matrix[0].length - 2], matrix[1][matrix[0].length - 2]));
        return neighbors;
    }

    private List<RGB> obtainToprightNeighbors(int[][] matrix) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[matrix.length - 2][0], matrix[matrix.length - 2][0], matrix[matrix.length - 2][0]));
        neighbors.add(new RGB(matrix[matrix.length - 1][1], matrix[matrix.length - 1][1], matrix[matrix.length - 1][1]));
        neighbors.add(new RGB(matrix[matrix.length - 2][1], matrix[matrix.length - 2][1], matrix[matrix.length - 2][1]));
        return neighbors;
    }

    private List<RGB> obtainTopleftNeighbors(int[][] matrix) {
        List<RGB> neighbors = new ArrayList<>();
        neighbors.add(new RGB(matrix[1][0], matrix[1][0], matrix[1][0]));
        neighbors.add(new RGB(matrix[0][1], matrix[0][1], matrix[0][1]));
        neighbors.add(new RGB(matrix[1][1], matrix[1][1], matrix[1][1]));
        return neighbors;
    }

    public boolean isPositionValid(int width, int height, int i, int j) {
        return j >= 0 && j < height && i >= 0 && i < width;
    }

    public boolean isSquare(int[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    public boolean isSquare(double[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    public int calculateTrace(int[][] matrix) {

        if (!isSquare(matrix)) {
            throw new RuntimeException("Trace of a non square matrix is not defined");
        }

        int trace = 0;
        for (int i = 0; i < matrix.length; i++) {
            trace += matrix[i][i];
        }

        return trace;
    }

    public double calculateTrace(double[][] matrix) {

        if (!isSquare(matrix)) {
            throw new RuntimeException("Trace of a non square matrix is not defined");
        }

        double trace = 0;
        for (int i = 0; i < matrix.length; i++) {
            trace += matrix[i][i];
        }

        return trace;
    }

    public int calculateDeterminant(int[][] matrix) {

        if (!isSquare(matrix)) {
            throw new RuntimeException("Determinant of a non square matrix is not defined");
        }

        if (is2x2(matrix)) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {

            int determinant = 0;
            for (int j = 0; j < matrix[0].length; j++) {

                int[][] complementaryMinor = calculateComplementaryMinor(matrix, j);

                determinant += (Math.pow(-1, j) * matrix[0][j] * this.calculateDeterminant(complementaryMinor));

            }

            return determinant;
        }
    }

    public double calculateDeterminant(double[][] matrix) {

        if (!isSquare(matrix)) {
            throw new RuntimeException("Determinant of a non square matrix is not defined");
        }

        if (is2x2(matrix)) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {

            double determinant = 0;
            for (int j = 0; j < matrix[0].length; j++) {

                double[][] complementaryMinor = calculateComplementaryMinor(matrix, j);

                determinant += (Math.pow(-1, j) * matrix[0][j] * this.calculateDeterminant(complementaryMinor));

            }

            return determinant;
        }
    }

    public int[][] calculateComplementaryMinor(int[][] matrix, int j) {

        int[][] auxMatrix = new int[matrix.length - 1][matrix[0].length - 1];

        int k = 0;
        int l = 0;
        for (int x = 1; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {

                if (y != j) {
                    auxMatrix[k][l] = matrix[x][y];
                    l++;
                }

            }
            k++;
            l = 0;
        }

        return auxMatrix;
    }

    public double[][] calculateComplementaryMinor(double[][] matrix, int j) {

        double[][] auxMatrix = new double[matrix.length - 1][matrix[0].length - 1];

        int k = 0;
        int l = 0;
        for (int x = 1; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {

                if (y != j) {
                    auxMatrix[k][l] = matrix[x][y];
                    l++;
                }

            }
            k++;
            l = 0;
        }

        return auxMatrix;
    }

    private boolean is2x2(int[][] matrix) {
        return matrix.length == 2 && matrix[0].length == 2;
    }

    private boolean is2x2(double[][] matrix) {
        return matrix.length == 2 && matrix[0].length == 2;
    }

    public int[][] calculateSquare(int[][] matrix) {
        return this.multiplyPointToPoint(matrix, matrix);
    }

    public int[][] multiplyPointToPoint(int[][] firstMatrix, int[][] secondMatrix) {

        if (sameSize(firstMatrix, secondMatrix)) {

            int[][] product = new int[firstMatrix.length][firstMatrix[0].length];
            for (int i = 0; i < product.length; i++) {
                for (int j = 0; j < product[0].length; j++) {

                    product[i][j] = firstMatrix[i][j] * secondMatrix[i][j];

                }
            }
            return product;

        } else {
            throw new RuntimeException("Matrix sizes are different. Cannot perform the point to point product");
        }
    }

    private boolean sameSize(int[][] firstMatrix, int[][] secondMatrix) {
        return firstMatrix.length == secondMatrix.length && firstMatrix[0].length == secondMatrix[0].length;
    }

    public double findMaxValue(double[][] matrix) {

        double max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }

        return max;
    }
}
