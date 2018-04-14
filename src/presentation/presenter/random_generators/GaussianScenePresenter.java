package presentation.presenter.random_generators;

import core.action.noise.ApplyGaussianNoiseToImageAction;
import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.semaphore.RandomGeneratorsSemaphore;
import core.service.statistics.RandomNumberGenerationService;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.GaussianSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.InsertValuePopup;
import presentation.util.ShowResultPopup;

public class GaussianScenePresenter {

    private final GaussianSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;
    private final ApplyGaussianNoiseToImageAction applyGaussianNoiseToImageAction;
    private final PublishSubject<Image> onModifiedImage;

    public GaussianScenePresenter(GaussianSceneController gaussianSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> imagePublishSubject, ApplyGaussianNoiseToImageAction applyGaussianNoiseToImageAction, PublishSubject<Image> onModifiedImage) {
        this.view = gaussianSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = imagePublishSubject;
        this.applyGaussianNoiseToImageAction = applyGaussianNoiseToImageAction;
        this.onModifiedImage = onModifiedImage;

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

                int randomNumberMatrix[][] = this.randomNumberGenerationService.generateRandomGaussianMatrix(100, 100, mu, sigma);
                Image image = this.generateSyntheticNoiseImageAction.execute(randomNumberMatrix);
                this.sendNoiseImageToNewWindow(image);
                this.view.closeWindow();

            } else { //Noise generator to apply to an existing image
                double percent = (Double.parseDouble(InsertValuePopup.show("Percent of noise", "0").get()))/100.00;
                Image image = this.applyGaussianNoiseToImageAction.execute(percent, mu, sigma);
                this.onModifiedImage.onNext(image);
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
