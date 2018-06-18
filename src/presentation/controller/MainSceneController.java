package presentation.controller;

import core.provider.PresenterProvider;
import core.provider.ViewProvider;
import domain.flags.LaplacianDetector;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import presentation.presenter.MainPresenter;
import presentation.view.CustomImageView;

public class MainSceneController {

    @FXML
    public Group groupImageView;
    @FXML
    public ImageView imageView;
    @FXML
    public ImageView modifiedImageView;
    @FXML
    public Button applyChangesButton;
    @FXML
    public Button resetModifiedImageButton;
    @FXML
    public TextField pixelX;
    @FXML
    public TextField pixelY;
    @FXML
    public TextField valueR;
    @FXML
    public TextField valueG;
    @FXML
    public TextField valueB;
    @FXML
    public Button undoChangesButton;

    public CustomImageView customImageView;

    private final MainPresenter mainPresenter;

    public MainSceneController() {

        this.mainPresenter = PresenterProvider.provideImageSelectionPresenter(this);
        ViewProvider.setMainView(this);
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        this.mainPresenter.initialize();
    }

    @FXML
    public void openImage() {
        this.mainPresenter.onOpenImage();
    }

    @FXML
    public void openImageSequence() {
        this.mainPresenter.onOpenImageSequence();
    }

    @FXML
    public void saveModifiedImage() {
        this.mainPresenter.onSaveImage();
    }

    @FXML
    public void applyChanges() {
        this.mainPresenter.onApplyChanges();
    }

    @FXML
    public void showGreyGradient() {
        this.mainPresenter.onShowGreyGradient();
    }

    @FXML
    public void showColorGradient() {
        this.mainPresenter.onShowColorGradient();
    }

    @FXML
    public void showRGBImageRedChannel() {
        this.mainPresenter.onShowRGBImageRedChannel();
    }

    @FXML
    public void showRGBImageGreenChannel() {
        this.mainPresenter.onShowRGBImageGreenChannel();
    }

    @FXML
    public void showRGBImageBlueChannel() {
        this.mainPresenter.onShowRGBImageBlueChannel();
    }

    @FXML
    public void showImageWithQuadrate() {
        this.mainPresenter.onShowImageWithQuadrate();
    }

    @FXML
    public void showImageWithCircle() {
        this.mainPresenter.onShowImageWithCircle();
    }

    @FXML
    public void showHueHSVChannel() {
        this.mainPresenter.onShowHueHSVChannel();
    }

    @FXML
    public void showSaturationHSVChannel() {
        this.mainPresenter.onShowSaturationHSVChannel();
    }

    @FXML
    public void showValueHSVChannel() {
        this.mainPresenter.onShowValueHSVChannel();
    }

    @FXML
    public void calculatePixelValue() {
        this.mainPresenter.onCalculatePixelValue();
    }

    @FXML
    public void modifyPixelValue() {
        this.mainPresenter.onModifyPixelValue();
    }

    @FXML
    public void showReport() {
        this.mainPresenter.onShowReport();
    }

    @FXML
    public void cutPartialImage() {
        this.mainPresenter.onCutPartialImage();
    }

    @FXML
    public void calculateImagesOperations() {
        this.mainPresenter.onCalculateImagesOperations();
    }

    @FXML
    public void calculateNegativeImage() {
        this.mainPresenter.onCalculateNegativeImage();
    }

    @FXML
    public void threshold() {
        this.mainPresenter.onThreshold();
    }

    @FXML
    public void contrast() {
        this.mainPresenter.onContrast();
    }

    @FXML
    public void compressDynamicRange() {
        this.mainPresenter.onCompressDynamicRange();
    }

    @FXML
    public void gammaPowerFunction() {
        this.mainPresenter.onGammaPowerFunction();
    }

    @FXML
    public void generateExponentialRandomNumber() {
        this.mainPresenter.onGenerateExponentialRandomNumber();
    }

    @FXML
    public void generateRayleighRandomNumber() {
        this.mainPresenter.onGenerateRayleighRandomNumber();
    }

    @FXML
    public void generateGaussianRandomNumber() {
        this.mainPresenter.onGenerateGaussianRandomNumber();
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void createImageHistogram() {
        this.mainPresenter.onCreateImageHistogram();
    }

    @FXML
    public void createEqualizedImage() {
        this.mainPresenter.onCreateEqualizedImageOnce();
    }

    @FXML
    public void createImageEqualizedTwice() {
        this.mainPresenter.onCreateEqualizedImageTwice();
    }

    @FXML
    public void applySaltAndPepperNoise() { this.mainPresenter.onApplySaltAndPepperNoise(); }

    public void onApplyEdgeEnhancement(){this.mainPresenter.onApplyEdgeEnhancement();}

    @FXML
    public void generateExponentialNoiseSyntheticImage() {
        this.mainPresenter.onGenerateExponentialNoiseSyntheticImage();
    }

    @FXML
    public void generateRayleighNoiseSyntheticImage() {
        this.mainPresenter.onGenerateRayleighNoiseSyntheticImage();
    }

    @FXML
    public void generateGaussianNoiseSyntheticImage() {
        this.mainPresenter.onGenerateGaussianNoiseSyntheticImage();
    }

    @FXML
    public void createEqualizedImageByHistogram() {
        this.mainPresenter.onCreateEqualizedImageByHistogram();
    }

    @FXML
    public void createImageEqualizedTwiceByHistogram() {
        this.mainPresenter.onCreateEqualizedImageTwiceByHistogram();
    }

    @FXML
    public void applyAdditiveGaussianNoise() { this.mainPresenter.onApplyAdditiveGaussianNoise(); }

    @FXML
    public void applyMultiplicativeRayleighNoise() { this.mainPresenter.onApplyMultiplicativeRayleighNoise(); }

    @FXML
    public void applyMultiplicativeExponentialNoise() { this.mainPresenter.onApplyMultiplicativeExponentialNoise(); }

    @FXML
    public void onApplyMeanFilter() {
        this.mainPresenter.onApplyMeanFilter();
    }

    @FXML
    public void onApplyMedianFilter() {
        this.mainPresenter.onApplyMedianFilter();
    }

    @FXML
    public void onApplyWeightedMedianFilter() {
        this.mainPresenter.onApplyWeightedMedianFilter();
    }

    @FXML
    public void onApplyGaussianFilter() {
        this.mainPresenter.onApplyGaussianFilter();
    }

    @FXML
    public void onApplyPrewittEdgeDetector() {
        this.mainPresenter.onApplyPrewittEdgeDetector();
    }

    @FXML
    public void onApplySobelEdgeDetector() {
        this.mainPresenter.onApplySobelEdgeDetector();
    }

    @FXML
    public void onApplyDirectionalDerivativeOperatorStandardMask() {
        this.mainPresenter.onApplyDirectionalDerivativeOperatorStandardMask();
    }

    @FXML
    public void onApplyDirectionalDerivativeOperatorKirshMask() {
        this.mainPresenter.onApplyDirectionalDerivativeOperatorKirshMask();
    }

    @FXML
    public void onApplyDirectionalDerivativeOperatorPrewittMask() {
        this.mainPresenter.onApplyDirectionalDerivativeOperatorPrewittMask();
    }

    @FXML
    public void onApplyDirectionalDerivativeOperatorSobelMask() {
        this.mainPresenter.onApplyDirectionalDerivativeOperatorSobelMask();
    }

    @FXML
    public void applyGlobalThresholdEstimation(){
        this.mainPresenter.onApplyGlobalThresholdEstimation();
    }

    @FXML
    public void applyOtsuThresholdEstimation(){
        this.mainPresenter.onApplyOtsuThresholdEstimation();
    }

    @FXML
    public void onApplyLaplacianEdgeDetector() {
        this.mainPresenter.onApplyLaplacianEdgeDetector(LaplacianDetector.STANDARD);
    }

    @FXML
    public void onApplyLaplacianEdgeDetectorWithSlopeEvaluation() {
        this.mainPresenter.onApplyLaplacianEdgeDetector(LaplacianDetector.WITH_SLOPE_EVALUATION);
    }

    @FXML
    public void onApplyMarrHildrethEdgeDetector() {
        this.mainPresenter.onApplyMarrHildrethEdgeDetector();
    }

    @FXML
    public void onApplyDiffusion() {
        this.mainPresenter.onApplyDiffusion();
    }

    @FXML
    public void resetModifiedImage() {
        this.mainPresenter.onResetModifiedImage();
    }

    @FXML
    public void onUndoChanges() {
        this.mainPresenter.onUndoChanges();
    }

    @FXML
    public void onApplyCannyEdgeDetector() {
        this.mainPresenter.onApplyCannyEdgeDetector();
    }

    @FXML
    public void applySusanEdgeDetector(){
        this.mainPresenter.onApplySusanEdgeDetector();
    }

    @FXML
    public void onHoughTransform() {
        this.mainPresenter.onHoughTransform();
    }

    @FXML
    public void onApplyActiveContour() {
        this.mainPresenter.onApplyActiveContour();
    }

    @FXML
    public void onApplyActiveContourOnImageSequence() {
        this.mainPresenter.onApplyActiveContourOnImageSequence();
    }

    @FXML
    public void onApplyHarris() { this.mainPresenter.onApplyHarris(); }

    @FXML
    public void onApplySift() { this.mainPresenter.onApplySift(); }
}
