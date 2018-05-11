package domain.diffusion;

public class AnisotropicWithLeclercEdgeDiffusion extends AnisotropicDiffusion {

    public AnisotropicWithLeclercEdgeDiffusion(double sigma) {
        super(sigma);
    }

    @Override
    public float applyGFunction(float derivative, double sigma) {
        return (float) Math.exp((-Math.pow(Math.abs(derivative), 2)) / Math.pow(sigma, 2));
    }
}
