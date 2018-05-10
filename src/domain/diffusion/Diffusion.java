package domain.diffusion;

public interface Diffusion {

    int apply(Derivative derivative);

    enum Type {
        ISOTROPIC,
        LORENTZ_ANISOTROPIC,
        LECLERC_ANISOTROPIC
    }
}
