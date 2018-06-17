import core.provider.ServiceProvider;
import core.service.MatrixService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MatrixTest {

    private static MatrixService matrixService;

    @BeforeClass
    public static void initializeMatrixService() {
        matrixService = ServiceProvider.provideMatrixService();
    }

    @Test
    public void checkIfIntMatrixIsSquareTest() {

        int[][] matrix = new int[4][4];
        Assert.assertEquals(true, matrixService.isSquare(matrix));

    }

    @Test
    public void checkIfIntMatrixIsNotSquareTest() {

        int[][] matrix = new int[4][5];
        Assert.assertEquals(false, matrixService.isSquare(matrix));

    }

    @Test
    public void checkIfDoubleMatrixIsSquareTest() {

        double[][] matrix = new double[4][4];
        Assert.assertEquals(true, matrixService.isSquare(matrix));

    }

    @Test
    public void checkIfDoubleMatrixIsNotSquareTest() {

        double[][] matrix = new double[4][5];
        Assert.assertEquals(false, matrixService.isSquare(matrix));

    }

    @Test
    public void traceMustBe4Test() {

        int[][] matrix = {{1,0,1},
                          {1,1,1},
                          {0,1,2}};

        Assert.assertEquals(4,matrixService.calculateTrace(matrix));
    }

    @Test
    public void traceMustBe0Test() {

        int[][] matrix = {{1,0,1},
                          {1,1,1},
                          {0,1,-2}};

        Assert.assertEquals(0,matrixService.calculateTrace(matrix));
    }

    @Test
    public void traceMustBe2Point5Test() {

        double[][] matrix = {{1,0,1.3},
                             {1,1.5,1},
                             {0.1,1,0}};

        Assert.assertEquals(2.5,matrixService.calculateTrace(matrix), 0.0);
    }

    @Test (expected=RuntimeException.class)
    public void nonSquareMatrixTraceIsNotDefined() {

        int[][] matrix = {{1,0,1,0},
                          {1,1,1,3},
                          {0,1,-2,-5}};

        matrixService.calculateTrace(matrix);

    }

    @Test
    public void determinantOfInt2x2MatrixMustBe1() {

        int[][] matrix = {{1, 0},
                          {0, 1}};

        Assert.assertEquals(1,matrixService.calculateDeterminant(matrix));


    }

    @Test
    public void firstComplementaryMinorIs1110() {

        int[][] matrix = {{1,0,1},
                          {0,1,1},
                          {1,1,0}};

        int[][] complementaryMinor = matrixService.calculateComplementaryMinor(matrix,0);

        Assert.assertArrayEquals(new int[] {1,1}, complementaryMinor[0]);
        Assert.assertArrayEquals(new int[] {1,0}, complementaryMinor[1]);

    }

    @Test
    public void secondComplementaryMinorIs0110() {

        int[][] matrix = {{1,0,1},
                          {0,1,1},
                          {1,1,0}};

        int[][] complementaryMinor = matrixService.calculateComplementaryMinor(matrix,1);

        Assert.assertArrayEquals(new int[] {0,1}, complementaryMinor[0]);
        Assert.assertArrayEquals(new int[] {1,0}, complementaryMinor[1]);

    }

    @Test
    public void thirdComplementaryMinorIs0111() {

        int[][] matrix = {{1,0,1},
                          {0,1,1},
                          {1,1,0}};

        int[][] complementaryMinor = matrixService.calculateComplementaryMinor(matrix,2);

        Assert.assertArrayEquals(new int[] {0,1}, complementaryMinor[0]);
        Assert.assertArrayEquals(new int[] {1,1}, complementaryMinor[1]);

    }

    @Test
    public void determinantOfInt3x3MatrixMustBeMinus2() {

        int[][] matrix = {{1,0,1},
                          {0,1,1},
                          {1,1,0}};

        Assert.assertEquals(-2,matrixService.calculateDeterminant(matrix));

    }

    @Test
    public void determinantOfInt4x4MatrixMustBe376() {

        int[][] matrix = {{2,3,3,6},
                          {2,3,6,7},
                          {4,82,0,3},
                          {2,23,2,3}};

        Assert.assertEquals(376,matrixService.calculateDeterminant(matrix));

    }

    @Test
    public void determinantOfDouble3x3MatrixMustBeZero() {

        double[][] matrix = {{1.5,1.5,0},
                          {3,3,0},
                          {2.2,2.7,1}};

        Assert.assertEquals(0,matrixService.calculateDeterminant(matrix), 0.0);

    }

}
