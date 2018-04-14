package presentation.presenter.random_generators;

import core.service.RandomNumberGenerationService;
import javafx.scene.control.Alert;
import presentation.controller.GaussianSceneController;
import presentation.controller.RayleighSceneController;
import presentation.util.ShowResultPopup;

public class GaussianScenePresenter {

    private final GaussianSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;

    public GaussianScenePresenter(GaussianSceneController gaussianSceneController, RandomNumberGenerationService randomNumberGenerationService) {
        this.view = gaussianSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
    }

    public void onGenerate() {

        double mu = Double.parseDouble(this.view.muTextField.getText());
        double sigma = Double.parseDouble(this.view.sigmaTextField.getText());

        if (isSigmaValid(sigma)) {
            double number = this.randomNumberGenerationService.generateGaussianNumber(mu, sigma);
            this.showNumber(number);
            this.view.closeWindow();
        }

    }

    private boolean isSigmaValid(double sigma) {
        return sigma > 0;
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Gaussian Random Number Generation", "Generated number: " + number);

    }

}
