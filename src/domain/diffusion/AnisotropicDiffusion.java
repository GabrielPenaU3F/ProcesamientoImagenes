package domain.diffusion;

public class AnisotropicDiffusion implements Diffusion {

    private final double sigma;

    public AnisotropicDiffusion(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public int apply(Derivative derivative) {
        float northDerivativeGradient = gradient(derivative.getNorth(), sigma);
        float sudDerivativeGradient = gradient(derivative.getSud(), sigma);
        float eastDerivativeGradient = gradient(derivative.getEast(), sigma);
        float westDerivativeGradient = gradient(derivative.getWest(), sigma);

        float northIij = derivative.getNorth() * northDerivativeGradient;
        float sudIij = derivative.getSud() * sudDerivativeGradient;
        float eastIij = derivative.getEast() * eastDerivativeGradient;
        float westIij = derivative.getWest() * westDerivativeGradient;

        float lambda = 0.25f;
        return (int) (derivative.getValue() + lambda * (northIij + sudIij + eastIij + westIij));
    }

    private float gradient(float derivative, double sigma) {
        return 1 / (((float) (Math.pow(Math.abs(derivative), 2) / Math.pow(sigma, 2))) + 1);
    }
}
