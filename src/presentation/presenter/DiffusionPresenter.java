package presentation.presenter;

import core.action.diffusion.ApplyDiffusionAction;
import core.action.image.GetImageAction;
import domain.diffusion.AnisotropicDiffusion;
import domain.diffusion.Diffusion;
import domain.diffusion.DiffusionSemaphore;
import domain.diffusion.IsotropicDiffusion;
import presentation.util.InsertValuePopup;

public class DiffusionPresenter {

    private GetImageAction getImageAction;
    private ApplyDiffusionAction applyDiffusionAction;

    public DiffusionPresenter(GetImageAction getImageAction, ApplyDiffusionAction applyDiffusionAction) {
        this.getImageAction = getImageAction;
        this.applyDiffusionAction = applyDiffusionAction;
    }

    public void onInitialize() {
        Integer iterations = Integer.parseInt(InsertValuePopup.show("Insert iterations", "0").get());
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    Diffusion diffusion = createDiffusion();
                    this.applyDiffusionAction.execute(customImage, diffusion, iterations);
                });
    }

    private Diffusion createDiffusion() {
        if(DiffusionSemaphore.getValue() == Diffusion.Type.ISOTROPIC) {
            return new IsotropicDiffusion();
        }

        Double sigma = Double.parseDouble(InsertValuePopup.show("Insert sigma", "0").get());
        return new AnisotropicDiffusion(sigma);
    }
}
