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
    private GetImageAction getImageAction;
    private ApplyDiffusionAction applyDiffusionAction;

    public DiffusionPresenter(DiffusionSceneController diffusionSceneController, GetImageAction getImageAction, ApplyDiffusionAction applyDiffusionAction) {
        this.view = diffusionSceneController;
        this.getImageAction = getImageAction;
        this.applyDiffusionAction = applyDiffusionAction;
    }

    public void onApplyDiffusion() {

        if (isIterationsNumberValid(view.iterationsTextField.getText())) {

            Diffusion diffusion;
            if (view.isotropic.isSelected()) {

                diffusion = createIsotropicDiffusion();
                this.executeDiffusion(diffusion);
                this.view.closeWindow();

            } else {

                if(isSigmaValid(view.sigmaTextField.getText())) {

                    if (view.leclerc.isSelected()) {
                        diffusion = createAnisotropicLeclercDiffusion(Integer.parseInt(view.sigmaTextField.getText()));
                    } else if (view.lorentz.isSelected()) {
                        diffusion = createAnisotropicLorentzDiffusion(Integer.parseInt(view.sigmaTextField.getText()));
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
                    this.applyDiffusionAction.execute(customImage, diffusion, Integer.parseInt(view.iterationsTextField.getText()));
                });
    }

    private Diffusion createAnisotropicLorentzDiffusion(int sigma) {
        return new AnisotropicWithLorentzEdgeDiffusion(sigma);
    }

    private Diffusion createAnisotropicLeclercDiffusion(int sigma) {
         return new AnisotropicWithLeclercEdgeDiffusion(sigma);
    }

    private Diffusion createIsotropicDiffusion() {
        return new IsotropicDiffusion();
    }

    private boolean isSigmaValid(String sigma) {
        return Integer.parseInt(sigma) > 0;
    }

    private boolean isIterationsNumberValid(String iterations) {
        return Integer.parseInt(iterations) > 0;
    }

}
