package presentation.presenter;

import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import core.action.edit.ModifyPixelAction;
import core.action.edit.space_domain.ApplyThresholdAction;
import core.action.edit.space_domain.CalculateNegativeImageAction;
import core.action.edit.space_domain.CompressDynamicRangeAction;
import core.action.figure.CreateImageWithFigureAction;
import core.action.gradient.CreateImageWithGradientAction;
import core.action.histogram.EqualizeGrayImageAction;
import core.action.edit.space_domain.ApplyThresholdAction;
import core.action.edit.space_domain.CalculateNegativeImageAction;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.modifiedimage.PutModifiedImageAction;
import domain.Channel;
import domain.Figure;
import domain.Gradient;
import domain.customimage.CustomImage;
import domain.customimage.Format;
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
                         CompressDynamicRangeAction compressDynamicRangeAction) {

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
        onModifiedImage.subscribe(image -> view.modifiedImageView.setImage(image));
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
    }

    public void onCalculatePixelValue() {
        if (this.validatePixelCoordinates()) {

            int pixelX = Integer.parseInt(view.pixelX.getText());
            int pixelY = Integer.parseInt(view.pixelY.getText());

            this.getImageAction.execute()
                    .map(customImage -> customImage.getPixelValue(pixelX, pixelY))
                    .ifPresent(rgb -> {
                        view.valueR.setText(rgb.getR().toString());
                        view.valueG.setText(rgb.getG().toString());
                        view.valueB.setText(rgb.getB().toString());
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
    }

    public void onThreshold() {
        view.modifiedImageView.setImage(applyThresholdAction.execute(InsertValuePopup.show("Threshold", "0").get()));
    }

    public void onCreateImageHistogram() {
        new ImageHistogramSceneCreator().createScene();
    }

    public void onContrast() {
        new ContrastSceneCreator().createScene();
    }

    public MainSceneController getView() {
        return this.view;
    }

    public void onCreateEqualizedImage() {
        view.modifiedImageView.setImage(equalizeGrayImageAction.execute());
    }

    public void onCreateEqualizedImageTwice() {
        view.modifiedImageView.setImage(equalizeGrayImageAction.executeTwice());
    }

    public void onCompressDynamicRange() {
        Image image = compressDynamicRangeAction.execute();
        view.modifiedImageView.setImage(image);
    }

    public void onGammaPowerFunction() {
        new GammaPowerFunctionSceneCreator().createScene();
    }

    public void onCalculateImagesOperations() {
        new ImagesOperationsSceneCreator().createScene();
    }

    public void onGenerateExponentialRandomNumber() { new ExponentialSceneCreator().createScene(); }

    public void onGenerateRayleighRandomNumber() { new RayleighSceneCreator().createScene(); }

    public void onGenerateGaussianRandomNumber() { new GaussianSceneCreator().createScene(); }
}
