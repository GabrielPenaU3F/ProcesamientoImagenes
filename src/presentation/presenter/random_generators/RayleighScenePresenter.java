package presentation.presenter.random_generators;

import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import core.provider.PublishSubjectProvider;
import core.semaphore.RandomGeneratorsSemaphore;
import core.service.statistics.RandomNumberGenerationService;
import domain.RandomElement;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.RayleighSceneController;
import presentation.scenecreator.NoiseImageSceneCreator;
import presentation.util.ShowResultPopup;

public class RayleighScenePresenter {


    private final RayleighSceneController view;
    private final RandomNumberGenerationService randomNumberGenerationService;
    private final GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private final PublishSubject<Image> onNoiseImage;

    public RayleighScenePresenter(RayleighSceneController rayleighSceneController, RandomNumberGenerationService randomNumberGenerationService, GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction, PublishSubject<Image> imagePublishSubject) {
        this.view = rayleighSceneController;
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.generateSyntheticNoiseImageAction = generateSyntheticNoiseImageAction;
        this.onNoiseImage = imagePublishSubject;
    }

    public void onGenerate() {

        double psi = Double.parseDouble(this.view.psiTextField.getText());

        if (RandomGeneratorsSemaphore.getValue() == RandomElement.NUMBER) {

            double number = this.randomNumberGenerationService.generateRayleighNumber(psi);
            this.showNumber(number);
            this.view.closeWindow();

        } else if (RandomGeneratorsSemaphore.getValue() == RandomElement.SYNTHETIC_NOISE_IMAGE){

            int randomNumberMatrix[][] = new int[100][100];
            for(int i=0; i < randomNumberMatrix.length; i++) {
                for (int j=0; j < randomNumberMatrix[i].length; j++) {

                    double number = this.randomNumberGenerationService.generateRayleighNumber(psi);
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

    private void sendNoiseImageToNewWindow(Image image) {
        new NoiseImageSceneCreator().createScene();
        onNoiseImage.onNext(image);
    }

    private void showNumber(double number) {
        ShowResultPopup.show("Rayleigh Random Number Generation", "Generated number: " + number);
    }

}
