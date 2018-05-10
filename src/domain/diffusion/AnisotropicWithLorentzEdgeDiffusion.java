package domain.diffusion;

public class AnisotropicWithLorentzEdgeDiffusion extends AnisotropicDiffusion {

    public AnisotropicWithLorentzEdgeDiffusion(double sigma) {
        super(sigma);
    }

    @Override
    public float applyGFunction(float derivative, double sigma) {
        return (float) (1 / ((Math.pow(Math.abs(derivative), 2) / Math.pow(sigma, 2)) + 1));
    }
}
