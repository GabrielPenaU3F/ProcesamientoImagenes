package presentation.presenter.random_generators;

import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.service.statistics.RandomNumberGenerationService;
import core.semaphore.RandomGeneratorsSemaphore;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.ExponentialSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.ShowResultPopup;

public class ExponentialScenePresenter {

    private final RandomNumberGenerationService randomNumberGenerationService;
    private final ExponentialSceneController view;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;

    public ExponentialScenePresenter(ExponentialSceneController exponentialSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> noiseImagePublishSubject) {
        this.view = exponentialSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = noiseImagePublishSubject;
    }

    public void onGenerate() {
        
        double lambda = Double.parseDouble(this.view.lambdaTextField.getText());

        if (isLambdaValid(lambda)) {

            if (RandomGeneratorsSemaphore.getValue() == RandomElement.NUMBER) {

                double number = this.randomNumberGenerationService.generateExponentialNumber(lambda);
                this.showNumber(number);
                this.view.closeWindow();

            } else if (RandomGeneratorsSemaphore.getValue() == RandomElement.SYNTHETIC_NOISE_IMAGE){

                int randomNumberMatrix[][] = new int[100][100];
                for(int i=0; i < randomNumberMatrix.length; i++) {
                    for (int j=0; j < randomNumberMatrix[i].length; j++) {

                        double number = this.randomNumberGenerationService.generateExponentialNumber(lambda);
                        randomNumberMatrix[i][j] = (int) (number*100); //This is a scale adjustment, just to avoid getting all zeros, in case the random number generated is < 1
                        
                    }
                }
                Image image = this.generateSyntheticNoiseImageAction.execute(randomNumberMatrix);
                this.sendNoiseImageToNewWindow(image);
                this.view.closeWindow();

            } else { //Noise generator to apply to an existing image
                //TODO
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
