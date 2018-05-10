package domain.diffusion;

public abstract class AnisotropicDiffusion implements Diffusion {

    private final double sigma;

    public AnisotropicDiffusion(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public int apply(Derivative derivative) {
        float northDerivativeG = applyGFunction(derivative.getNorth(), sigma);
        float southDerivativeG = applyGFunction(derivative.getSouth(), sigma);
        float eastDerivativeG = applyGFunction(derivative.getEast(), sigma);
        float westDerivativeG = applyGFunction(derivative.getWest(), sigma);

        float northIij = derivative.getNorth() * northDerivativeG;
        float southIij = derivative.getSouth() * southDerivativeG;
        float eastIij = derivative.getEast() * eastDerivativeG;
        float westIij = derivative.getWest() * westDerivativeG;

        float lambda = 0.25f;

        return (int) (derivative.getValue() + lambda * (northIij + southIij + eastIij + westIij));
    }

    public abstract float applyGFunction(float derivative, double sigma);
}
