package presentation.presenter.random_generators;

import core.action.noise.generator.RandomNumberGenerationService;
import presentation.controller.ExponentialSceneController;
import presentation.util.ShowResultPopup;

public class ExponentialScenePresenter {

    private final RandomNumberGenerationService randomNumberGenerationService;
    private final ExponentialSceneController view;

    public ExponentialScenePresenter(ExponentialSceneController exponentialSceneController, RandomNumberGenerationService randomNumberGenerationService) {
        this.view = exponentialSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
    }

    public void onGenerate() {
        
        double lambda = Double.parseDouble(this.view.lambdaTextField.getText());

        if (isLambdaValid(lambda)) {
            double number = this.randomNumberGenerationService.generateExponentialNumber(lambda);
            this.showNumber(number);
            this.view.closeWindow();
        }
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Exponential Random Number Generation", "Generated number: " + number);
    }

    private boolean isLambdaValid(double lambda) {
        return lambda > 0;
    }
}
