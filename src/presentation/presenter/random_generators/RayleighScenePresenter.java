package presentation.presenter.random_generators;

import core.service.RandomNumberGenerationService;
import javafx.scene.control.Alert;
import presentation.controller.RayleighSceneController;
import presentation.util.ShowResultPopup;

public class RayleighScenePresenter {


    private final RayleighSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;

    public RayleighScenePresenter(RayleighSceneController rayleighSceneController, RandomNumberGenerationService randomNumberGenerationService) {
        this.view = rayleighSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
    }

    public void onGenerate() {

        double psi = Double.parseDouble(this.view.psiTextField.getText());
        double number = this.randomNumberGenerationService.generateRayleighNumber(psi);
        this.showNumber(number);
        this.view.closeWindow();

    }

    private void showNumber(double number) {
        ShowResultPopup.show("Rayleigh Random Number Generation", "Generated number: " + number);
    }

}
