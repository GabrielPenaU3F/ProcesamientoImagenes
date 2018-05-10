package presentation.presenter;

import core.action.diffusion.ApplyDiffusionAction;
import core.action.image.GetImageAction;
import domain.diffusion.AnisotropicWithLeclercEdgeDiffusion;
import domain.diffusion.AnisotropicWithLorentzEdgeDiffusion;
import domain.diffusion.Diffusion;
import domain.diffusion.IsotropicDiffusion;
import presentation.controller.DiffusionSceneController;

public class DiffusionPresenter {

    private final DiffusionSceneController view;
    private final GetImageAction getImageAction;
    private final ApplyDiffusionAction applyDiffusionAction;

    public DiffusionPresenter(DiffusionSceneController diffusionSceneController, GetImageAction getImageAction,
            ApplyDiffusionAction applyDiffusionAction) {

        this.view = diffusionSceneController;
        this.getImageAction = getImageAction;
        this.applyDiffusionAction = applyDiffusionAction;
    }

    public void onApplyDiffusion() {

        if (isIterationsNumberValid(view.getIterations())) {

            Diffusion diffusion;
            if (view.isIsotropicSelected()) {

                diffusion = createIsotropicDiffusion();
                this.executeDiffusion(diffusion);
                this.view.closeWindow();

            } else if (view.isAnisotropicSelected()) {

                if (isSigmaValid(view.getSigma())) {

                    if (view.isLeclercSelected()) {
                        diffusion = createAnisotropicLeclercDiffusion(view.getSigma());
                    } else if (view.isLorentzSelected()) {
                        diffusion = createAnisotropicLorentzDiffusion(view.getSigma());
                    } else {
                        throw new RuntimeException("No diffusion selected");
                    }

                    this.executeDiffusion(diffusion);
                    this.view.closeWindow();
                }
            }
        }
    }

    private void executeDiffusion(Diffusion diffusion) {
        this.getImageAction.execute()
                           .ifPresent(customImage -> {
                               this.applyDiffusionAction.execute(customImage, diffusion, view.getIterations());
                           });
    }

    private Diffusion createAnisotropicLorentzDiffusion(double sigma) {
        return new AnisotropicWithLorentzEdgeDiffusion(sigma);
    }

    private Diffusion createAnisotropicLeclercDiffusion(double sigma) {
        return new AnisotropicWithLeclercEdgeDiffusion(sigma);
    }

    private Diffusion createIsotropicDiffusion() {
        return new IsotropicDiffusion();
    }

    private boolean isSigmaValid(double sigma) {
        return sigma > 0;
    }

    private boolean isIterationsNumberValid(int iterations) {
        return iterations > 0;
    }
}
