package presentation.presenter.random_generators;

import core.action.noise.ApplyExponentialNoiseToImageAction;
import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.service.statistics.RandomNumberGenerationService;
import core.semaphore.RandomGeneratorsSemaphore;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.ExponentialSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.InsertValuePopup;
import presentation.util.ShowResultPopup;

public class ExponentialScenePresenter {

    private final RandomNumberGenerationService randomNumberGenerationService;
    private final ExponentialSceneController view;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;
    private final ApplyExponentialNoiseToImageAction applyExponentialNoiseToImageAction;
    private final PublishSubject<Image> onModifiedImage;

    public ExponentialScenePresenter(ExponentialSceneController exponentialSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> noiseImagePublishSubject, ApplyExponentialNoiseToImageAction applyExponentialNoiseToImageAction, PublishSubject<Image> onModifiedImage) {
        this.view = exponentialSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = noiseImagePublishSubject;
        this.applyExponentialNoiseToImageAction = applyExponentialNoiseToImageAction;
        this.onModifiedImage = onModifiedImage;
    }

    public void onGenerate() {
        
        double lambda = Double.parseDouble(this.view.lambdaTextField.getText());

        if (isLambdaValid(lambda)) {

            if (RandomGeneratorsSemaphore.getValue() == RandomElement.NUMBER) {

                double number = this.randomNumberGenerationService.generateExponentialNumber(lambda);
                this.showNumber(number);
                this.view.closeWindow();

            } else if (RandomGeneratorsSemaphore.getValue() == RandomElement.SYNTHETIC_NOISE_IMAGE){

                int randomNumberMatrix[][] = this.randomNumberGenerationService.generateRandomExponentialMatrix(100, 100, lambda);
                Image image = this.generateSyntheticNoiseImageAction.execute(randomNumberMatrix);
                this.sendNoiseImageToNewWindow(image);
                this.view.closeWindow();

            } else { //Noise generator to apply to an existing image
                double percent = (Double.parseDouble(InsertValuePopup.show("Percent of noise", "0").get()))/100.00;
                Image image = this.applyExponentialNoiseToImageAction.execute(percent, lambda);
                this.onModifiedImage.onNext(image);
                this.view.closeWindow();
            }
        }
    }

    public void sendNoiseImageToNewWindow(Image image) {
        new NoiseImageSceneCreator().createScene();
        onNoiseImage.onNext(image);
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Exponential Random Number Generation", "Generated number: " + number);
    }

    private boolean isLambdaValid(double lambda) {
        return lambda > 0;
    }
}
