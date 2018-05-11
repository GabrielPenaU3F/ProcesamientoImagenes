package presentation.presenter;

import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import core.action.edit.ModifyPixelAction;
import core.action.edit.space_domain.ApplyThresholdAction;
import core.action.edit.space_domain.CalculateNegativeImageAction;
import core.action.edit.space_domain.CompressDynamicRangeAction;
import core.action.figure.CreateImageWithFigureAction;
import core.action.filter.ApplyFilterAction;
import core.action.gradient.CreateImageWithGradientAction;
import core.action.histogram.EqualizeGrayImageAction;
import core.action.histogram.utils.EqualizedTimes;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.PutModifiedImageAction;
import core.action.image.UpdateCurrentImageAction;
import core.provider.PresenterProvider;
import core.semaphore.RandomGeneratorsSemaphore;
import domain.RandomElement;
import domain.customimage.CustomImage;
import domain.customimage.Format;
import domain.FilterSemaphore;
import domain.diffusion.Diffusion;
import domain.diffusion.DiffusionSemaphore;
import domain.mask.Mask;
import domain.mask.filter.HighPassMask;
import domain.generation.Channel;
import domain.generation.Figure;
import domain.generation.Gradient;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import presentation.controller.MainSceneController;
import presentation.scenecreator.*;
import presentation.util.InsertValuePopup;
import presentation.view.CustomImageView;

public class MainPresenter {

    private static final String EMPTY = "";
    private static final int DEFAULT_WIDTH = 510;
    private static final int DEFAULT_HEIGHT = 510;

    private final MainSceneController view;
    private final LoadImageAction loadImageAction;
    private final GetImageAction getImageAction;
    private final ModifyPixelAction modifyPixelAction;
    private final PutModifiedImageAction putModifiedImageAction;
    private final CalculateNegativeImageAction calculateNegativeImageAction;
    private final ApplyThresholdAction applyThresholdAction;
    private final CreateImageWithGradientAction createImageWithGradientAction;
    private final ObtainRGBChannelAction obtainRGBChannelAction;
    private final ObtainHSVChannelAction obtainHSVChannelAction;
    private final CreateImageWithFigureAction createImageWithFigureAction;
    private final EqualizeGrayImageAction equalizeGrayImageAction;
    private final Observable<Image> onModifiedImage;
    private final CompressDynamicRangeAction compressDynamicRangeAction;
    private final ApplyFilterAction applyFilterAction;
    private final UpdateCurrentImageAction updateCurrentImageAction;

    public MainPresenter(MainSceneController view,
                         LoadImageAction loadImageAction,
                         GetImageAction getImageAction,
                         PutModifiedImageAction putModifiedImageAction,
                         ModifyPixelAction modifyPixelAction,
                         CalculateNegativeImageAction calculateNegativeImageAction,
                         ApplyThresholdAction applyThresholdAction,
                         CreateImageWithGradientAction createImageWithGradientAction,
                         ObtainRGBChannelAction obtainRGBChannelAction,
                         ObtainHSVChannelAction obtainHSVChannelAction,
                         CreateImageWithFigureAction createImageWithFigureAction,
                         EqualizeGrayImageAction equalizeGrayImageAction,
                         Observable<Image> onModifiedImage,
                         CompressDynamicRangeAction compressDynamicRangeAction,
                         ApplyFilterAction applyFilterAction,
                         UpdateCurrentImageAction updateCurrentImageAction) {

        this.view = view;

        this.loadImageAction = loadImageAction;
        this.getImageAction = getImageAction;
        this.modifyPixelAction = modifyPixelAction;
        this.putModifiedImageAction = putModifiedImageAction;
        this.calculateNegativeImageAction = calculateNegativeImageAction;
        this.applyThresholdAction = applyThresholdAction;
        this.onModifiedImage = onModifiedImage;
        this.createImageWithGradientAction = createImageWithGradientAction;
        this.obtainRGBChannelAction = obtainRGBChannelAction;
        this.obtainHSVChannelAction = obtainHSVChannelAction;
        this.createImageWithFigureAction = createImageWithFigureAction;
        this.equalizeGrayImageAction = equalizeGrayImageAction;
        this.compressDynamicRangeAction = compressDynamicRangeAction;
        this.applyFilterAction = applyFilterAction;
        this.updateCurrentImageAction = updateCurrentImageAction;
    }

    public void initialize() {
        view.customImageView = new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withOnPixelClick(this::onPixelClick)
                .withSelectionMode();

        awaitingForNewModifiedImages();
    }

    private Action onPixelClick(Integer x, Integer y) {
        return () -> {
            view.pixelX.setText(x.toString());
            view.pixelY.setText(y.toString());
            onCalculatePixelValue();
        };
    }

    private void awaitingForNewModifiedImages() {
        onModifiedImage.subscribe(image -> {
            putModifiedImageAction.execute(new CustomImage(SwingFXUtils.fromFXImage(image, null), Format.PNG));
            view.modifiedImageView.setImage(image);
        });
    }

    public void onOpenImage() {
        setImageOnCustomImageView(this.loadImageAction.execute());
    }

    private void setImageOnCustomImageView(CustomImage customImage) {
        view.customImageView.setImage(SwingFXUtils.toFXImage(customImage.getBufferedImage(), null));
    }

    public void onSaveImage() {
        new SaveImageSceneCreator().createScene();
    }

    public void onApplyChanges() {
        CustomImage modifiedCustomImage = new CustomImage(view.modifiedImageView.getImage(), "png");
        view.customImageView.setImage(view.modifiedImageView.getImage());
        updateCurrentImageAction.execute(modifiedCustomImage);
        view.modifiedImageView.setImage(null);
        view.applyChangesButton.setVisible(false);
    }

    public void onShowGreyGradient() {
        setImageOnCustomImageView(createImageWithGradientAction.execute(DEFAULT_WIDTH, DEFAULT_HEIGHT, Gradient.GREY));
    }

    public void onShowColorGradient() {
        setImageOnCustomImageView(createImageWithGradientAction.execute(DEFAULT_WIDTH, DEFAULT_HEIGHT, Gradient.COLOR));
    }

    public void onShowRGBImageRedChannel() {
        setImageOnModifiedImageView(obtainRGBChannelAction.execute(Channel.RED));
    }

    public void onShowRGBImageGreenChannel() {
        setImageOnModifiedImageView(obtainRGBChannelAction.execute(Channel.GREEN));
    }

    public void onShowRGBImageBlueChannel() {
        setImageOnModifiedImageView(obtainRGBChannelAction.execute(Channel.BLUE));
    }

    public void onShowImageWithQuadrate() {
        setImageOnCustomImageView(createImageWithFigureAction.execute(200, 200, Figure.QUADRATE));
    }

    public void onShowImageWithCircle() {
        setImageOnCustomImageView(createImageWithFigureAction.execute(200, 200, Figure.CIRCLE));
    }

    public void onShowHueHSVChannel() {
        setImageOnModifiedImageView(obtainHSVChannelAction.execute(Channel.HUE));
    }

    public void onShowSaturationHSVChannel() {
        setImageOnModifiedImageView(obtainHSVChannelAction.execute(Channel.SATURATION));
    }

    public void onShowValueHSVChannel() {
        setImageOnModifiedImageView(obtainHSVChannelAction.execute(Channel.VALUE));
    }

    private void setImageOnModifiedImageView(CustomImage customImage) {
        view.modifiedImageView.setImage(SwingFXUtils.toFXImage(customImage.getBufferedImage(), null));
        view.applyChangesButton.setVisible(true);
    }

    public void onCalculatePixelValue() {
        if (this.validatePixelCoordinates()) {

            int pixelX = Integer.parseInt(view.pixelX.getText());
            int pixelY = Integer.parseInt(view.pixelY.getText());

            this.getImageAction.execute()
                    .map(customImage -> customImage.getPixelValue(pixelX, pixelY))
                    .ifPresent(rgb -> {
                        view.valueR.setText(String.valueOf(rgb.getRed()));
                        view.valueG.setText(String.valueOf(rgb.getGreen()));
                        view.valueB.setText(String.valueOf(rgb.getBlue()));
                    });

        } else {
            view.valueR.setText("Error");
        }
    }

    public void onModifyPixelValue() {
        if (this.validatePixelCoordinates()) {

            Integer pixelX = Integer.parseInt(view.pixelX.getText());
            Integer pixelY = Integer.parseInt(view.pixelY.getText());

            String valueR = InsertValuePopup.show("Insert value R", "0").get();
            String valueG = InsertValuePopup.show("Insert value G", "0").get();
            String valueB = InsertValuePopup.show("Insert value B", "0").get();

            Image modifiedImage = modifyPixelAction.execute(pixelX, pixelY, valueR, valueG, valueB);

            view.modifiedImageView.setImage(modifiedImage);

        } else {
            view.valueR.setText("Select pixel");
            view.valueG.setText("Select pixel");
            view.valueB.setText("Select pixel");
        }
    }

    private boolean validatePixelCoordinates() {
        return (!view.pixelX.getText().equals(EMPTY) && !view.pixelY.getText().equals(EMPTY));
    }

    public void onShowReport() {
        new ImageInformSceneCreator().createScene();
    }

    public void onCutPartialImage() {
        Image image = view.customImageView.cutPartialImage();
        view.modifiedImageView.setImage(image);
        this.putModifiedImageAction.execute(new CustomImage(SwingFXUtils.fromFXImage(image, null), Format.PNG));
    }

    public void onCalculateNegativeImage() {
        Image image = this.calculateNegativeImageAction.execute();
        view.modifiedImageView.setImage(image);
        view.applyChangesButton.setVisible(true);
    }

    private void applyThresholdToModifiedImage(CustomImage customImage) {
        applyThreshold(customImage);
    }

    public void onThreshold() {
        this.getImageAction.execute().ifPresent(this::applyThreshold);
    }

    private void applyThreshold(CustomImage customImage) {
        int threshold = Integer.parseInt(InsertValuePopup.show("Threshold", "0").get());
        view.modifiedImageView.setImage(applyThresholdAction.execute(customImage, threshold));
        view.applyChangesButton.setVisible(true);
    }

    public void onCreateImageHistogram() {
        new ImageHistogramSceneCreator().createScene();
    }

    public void onContrast() {
        new ContrastSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public MainSceneController getView() {
        return this.view;
    }

    public void onCreateEqualizedImageOnce() {
        this.getImageAction.execute()
                .ifPresent(customImage -> equalizeGrayImageAction.execute(customImage, 1));
        view.applyChangesButton.setVisible(true);
    }

    public void onCreateEqualizedImageTwice() {
        this.getImageAction.execute()
                .ifPresent(customImage -> equalizeGrayImageAction.execute(customImage, 2));
        view.applyChangesButton.setVisible(true);
    }

    public void onCompressDynamicRange() {
        Image image = compressDynamicRangeAction.execute();
        view.modifiedImageView.setImage(image);
        view.applyChangesButton.setVisible(true);
    }

    public void onGammaPowerFunction() {
        new GammaPowerFunctionSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onCalculateImagesOperations() {
        new ImagesOperationsSceneCreator().createScene();
    }

    public void onGenerateExponentialRandomNumber() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NUMBER);
        new ExponentialSceneCreator().createScene();
    }

    public void onGenerateRayleighRandomNumber() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NUMBER);
        new RayleighSceneCreator().createScene();
    }

    public void onGenerateGaussianRandomNumber() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NUMBER);
        new GaussianSceneCreator().createScene();
    }

    public void onApplySaltAndPepperNoise() {
        new SaltAndPepperNoiseSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyEdgeEnhancement() {
        int insertedSize = 0;
        while (insertedSize % 2 == 0 || insertedSize <= 0) {
            insertedSize = Integer.parseInt(InsertValuePopup.show("Insert High Pass mask size (odd)", "3").get());
        }
        //hago esto, porque sino una expresion lambda que la usa despues tiene problemas
        int size = insertedSize;
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    CustomImage filteredCustomImage = applyFilterAction.execute(customImage, new HighPassMask(size));
                    view.modifiedImageView.setImage(filteredCustomImage.toFXImage());

                    this.applyThresholdToModifiedImage(filteredCustomImage);
                });
        view.applyChangesButton.setVisible(true);
    }

    public void onGenerateExponentialNoiseSyntheticImage() {
        RandomGeneratorsSemaphore.setValue(RandomElement.SYNTHETIC_NOISE_IMAGE);
        new ExponentialSceneCreator().createScene();
    }

    public void onGenerateRayleighNoiseSyntheticImage() {
        RandomGeneratorsSemaphore.setValue(RandomElement.SYNTHETIC_NOISE_IMAGE);
        new RayleighSceneCreator().createScene();
    }

    public void onGenerateGaussianNoiseSyntheticImage() {
        RandomGeneratorsSemaphore.setValue(RandomElement.SYNTHETIC_NOISE_IMAGE);
        new GaussianSceneCreator().createScene();
    }

    public void onCreateEqualizedImageByHistogram() {
        EqualizedTimes.once();
        new EqualizeImageByHistogramSceneCreator().createScene();
    }

    public void onCreateEqualizedImageTwiceByHistogram() {
        EqualizedTimes.twice();
        new EqualizeImageByHistogramSceneCreator().createScene();
    }

    public void onApplyAdditiveGaussianNoise() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NOISE);
        new GaussianSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyMultiplicativeRayleighNoise() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NOISE);
        new RayleighSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyMultiplicativeExponentialNoise() {
        RandomGeneratorsSemaphore.setValue(RandomElement.NOISE);
        new ExponentialSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyMeanFilter() {
        FilterSemaphore.setValue(Mask.Type.MEAN);
        new FilterSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyMedianFilter() {
        FilterSemaphore.setValue(Mask.Type.MEDIAN);
        new FilterSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyWeightedMedianFilter() {
        FilterSemaphore.setValue(Mask.Type.WEIGHTED_MEDIAN);
        new FilterSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyGaussianFilter() {
        FilterSemaphore.setValue(Mask.Type.GAUSSIAN);
        new FilterSceneCreator().createScene();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyPrewittEdgeDetector() {
        FilterSemaphore.setValue(Mask.Type.PREWITT);
        PresenterProvider.provideEdgeDetectorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplySobelEdgeDetector() {
        FilterSemaphore.setValue(Mask.Type.SOBEL);
        PresenterProvider.provideEdgeDetectorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyDirectionalDerivativeOperatorStandardMask() {
        FilterSemaphore.setValue(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_STANDARD);
        PresenterProvider.provideDirectionalDerivativeOperatorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyDirectionalDerivativeOperatorKirshMask() {
        FilterSemaphore.setValue(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH);
        PresenterProvider.provideDirectionalDerivativeOperatorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyDirectionalDerivativeOperatorPrewittMask() {
        FilterSemaphore.setValue(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_PREWITT);
        PresenterProvider.provideDirectionalDerivativeOperatorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyDirectionalDerivativeOperatorSobelMask() {
        FilterSemaphore.setValue(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_SOBEL);
        PresenterProvider.provideDirectionalDerivativeOperatorPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyIsotropicDiffusion() {
        DiffusionSemaphore.setValue(Diffusion.Type.ISOTROPIC);
        PresenterProvider.provideDiffusionPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyAnisotropicDiffusionWithLorentzEdgeDetection() {
        DiffusionSemaphore.setValue(Diffusion.Type.LORENTZ_ANISOTROPIC);
        PresenterProvider.provideDiffusionPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onApplyAnisotropicDiffusionWithLeclercEdgeDetection() {
        DiffusionSemaphore.setValue(Diffusion.Type.LECLERC_ANISOTROPIC);
        PresenterProvider.provideDiffusionPresenter().onInitialize();
        view.applyChangesButton.setVisible(true);
    }

    public void onResetModifiedImage() {
        view.modifiedImageView.setImage(null);
        view.applyChangesButton.setVisible(false);
    }
}
