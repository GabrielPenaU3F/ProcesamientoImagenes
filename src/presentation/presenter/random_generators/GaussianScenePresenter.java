package presentation.presenter.random_generators;

import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.semaphore.RandomGeneratorsSemaphore;
import core.service.statistics.RandomNumberGenerationService;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.GaussianSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.ShowResultPopup;

public class GaussianScenePresenter {

    private final GaussianSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;

    public GaussianScenePresenter(GaussianSceneController gaussianSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> imagePublishSubject) {
        this.view = gaussianSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = imagePublishSubject;
    }

    public void onGenerate() {

        double mu = Double.parseDouble(this.view.muTextField.getText());
        double sigma = Double.parseDouble(this.view.sigmaTextField.getText());

        if (isSigmaValid(sigma)) {

            if (RandomGeneratorsSemaphore.getValue() == RandomElement.NUMBER) {

                double number = this.randomNumberGenerationService.generateGaussianNumber(mu, sigma);
                this.showNumber(number);
                this.view.closeWindow();

            } else if (RandomGeneratorsSemaphore.getValue() == RandomElement.SYNTHETIC_NOISE_IMAGE){

                int randomNumberMatrix[][] = new int[100][100];
                for(int i=0; i < randomNumberMatrix.length; i++) {
                    for (int j=0; j < randomNumberMatrix[i].length; j++) {

                        double number = this.randomNumberGenerationService.generateGaussianNumber(mu, sigma);
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

    private void sendNoiseImageToNewWindow(Image image) {
        new NoiseImageSceneCreator().createScene();
        onNoiseImage.onNext(image);
    }

    private boolean isSigmaValid(double sigma) {
        return sigma > 0;
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Gaussian Random Number Generation", "Generated number: " + number);

    }

}
