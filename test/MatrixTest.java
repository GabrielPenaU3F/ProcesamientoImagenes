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

}
