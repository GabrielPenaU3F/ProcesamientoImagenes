package domain.mask;

public class LaplacianMask extends Mask {

    public LaplacianMask() {
        super(Type.LAPLACIAN, AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][] {
                { 0, -1, 0 },
                { -1, 4, -1 },
                { 0, -1, 0 }
        };
    }
}
