package presentation.presenter.random_generators;

import core.action.noise.ApplyRayleighNoiseToImageAction;
import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.provider.PublishSubjectProvider;
import core.semaphore.RandomGeneratorsSemaphore;
import core.service.statistics.RandomNumberGenerationService;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.RayleighSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.InsertValuePopup;
import presentation.util.ShowResultPopup;

public class RayleighScenePresenter {


    private final RayleighSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;
    private final ApplyRayleighNoiseToImageAction applyRayleighNoiseToImageAction;
    private final PublishSubject<Image> onModifiedImage;

    public RayleighScenePresenter(RayleighSceneController rayleighSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> imagePublishSubject, ApplyRayleighNoiseToImageAction applyRayleighNoiseToImageAction, PublishSubject<Image> onModifiedImage) {
        this.view = rayleighSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = imagePublishSubject;
        this.applyRayleighNoiseToImageAction = applyRayleighNoiseToImageAction;
        this.onModifiedImage = onModifiedImage;
    }

    public void onGenerate() {

        double psi = Double.parseDouble(this.view.psiTextField.getText());

        if (RandomGeneratorsSemaphore.getValue() == RandomElement.NUMBER) {

            double number = this.randomNumberGenerationService.generateRayleighNumber(psi);
            this.showNumber(number);
            this.view.closeWindow();

        } else if (RandomGeneratorsSemaphore.getValue() == RandomElement.SYNTHETIC_NOISE_IMAGE){

            int randomNumberMatrix[][] = this.randomNumberGenerationService.generateRandomRayleighMatrix(100,100, psi);
            Image image = this.generateSyntheticNoiseImageAction.execute(randomNumberMatrix);
            this.sendNoiseImageToNewWindow(image);
            this.view.closeWindow();

        } else { //Noise generator to apply to an existing image
            double percent = (Double.parseDouble(InsertValuePopup.show("Percent of noise", "0").get()))/100.00;
            Image image = this.applyRayleighNoiseToImageAction.execute(percent, psi);
            this.onModifiedImage.onNext(image);
            this.view.closeWindow();
        }
    }

    private void sendNoiseImageToNewWindow(Image image) {
        new NoiseImageSceneCreator().createScene();
        onNoiseImage.onNext(image);
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Rayleigh Random Number Generation", "Generated number: " + number);
    }

}
