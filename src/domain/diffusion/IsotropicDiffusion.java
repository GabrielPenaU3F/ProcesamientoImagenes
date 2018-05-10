package domain.diffusion;

public class IsotropicDiffusion implements Diffusion {
    
    @Override
    public int apply(Derivative derivative) {
        float lambda = 0.25f;
        float sum = derivative.getNorth() + derivative.getSouth() + derivative.getEast() + derivative.getWest();
        return (int)(derivative.getValue() + lambda * sum);
    }
}