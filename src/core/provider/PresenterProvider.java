package core.provider;

import presentation.controller.*;
import presentation.presenter.*;
import presentation.presenter.random_generators.ExponentialScenePresenter;
import presentation.presenter.random_generators.GaussianScenePresenter;
import presentation.presenter.random_generators.RayleighScenePresenter;
import presentation.presenter.space_domain_operations.ContrastScenePresenter;
import presentation.presenter.space_domain_operations.GammaScenePresenter;
import presentation.presenter.space_domain_operations.ImageHistogramPresenter;

public class PresenterProvider {

    private static MainPresenter mainPresenter;
    private static ImagesOperationsPresenter imagesOperationsPresenter;

    public static MainPresenter provideImageSelectionPresenter(MainSceneController mainSceneController) {
        if (mainPresenter == null) {
            mainPresenter = new MainPresenter(
                    mainSceneController,
                    ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.providePutModifiedImageAction(),
                    ActionProvider.provideModifyPixelAction(),
                    ActionProvider.provideCalculateNegativeImageAction(),
                    ActionProvider.provideApplyThresholdAction(),
                    ActionProvider.provideCreateGradientAction(),
                    ActionProvider.provideObtainRGBChannelAction(),
                    ActionProvider.provideObtainHSVChannelAction(),
                    ActionProvider.provideCreateImageWithFigureAction(),
                    ActionProvider.provideCreateEqualizedGrayImageAction(
                            PublishSubjectProvider.provideOnModifiedImagePublishSubject()),
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject(),
                    ActionProvider.provideCompressDynamicRangeAction());

            return mainPresenter;
        }
        return mainPresenter;
    }

    public static ImageReportPresenter provideImageInformPresenter() {
        return new ImageReportPresenter(
                ActionProvider.provideGetModifiedImageAction(),
                ActionProvider.provideCreateImageInformAction());
    }

    public static SaveImagePresenter provideSaveImagePresenter(SaveImageController saveImageController) {
        return new SaveImagePresenter(saveImageController,
                ActionProvider.provideGetModifiedImageAction(),
                ActionProvider.provideSaveImageAction());

    }

    public static ImageHistogramPresenter provideImageHistogramPresenter(ImageHistogramSceneController view) {
        return new ImageHistogramPresenter(view,
                ActionProvider.provideGetImageAction(),
                ActionProvider.provideCreateImageHistogram());
    }

    public static ContrastScenePresenter provideContrastScenePresenter(ContrastSceneController contrastSceneController) {
        return new ContrastScenePresenter(contrastSceneController,
                ActionProvider.provideApplyContrastAction(),
                ActionProvider.provideGetImageAction(),
                ServiceProvider.provideGrayLevelStatisticsService(),
                PublishSubjectProvider.provideOnModifiedImagePublishSubject());
    }

    public static GammaScenePresenter provideGammaScenePresenter(GammaSceneController gammaSceneController) {
        return new GammaScenePresenter(gammaSceneController,
                ActionProvider.provideGammaFunctionAction(),
                PublishSubjectProvider.provideOnModifiedImagePublishSubject());
    }

    public static ImagesOperationsPresenter provideImagesOperationPresenter() {
        if (imagesOperationsPresenter == null) {
            imagesOperationsPresenter = new ImagesOperationsPresenter(ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideNormalizeImageAction(), ActionProvider.provideSumImagesAction(),
                    ActionProvider.provideMultiplyImagesAction(), ActionProvider.provideMultiplyImageWithScalarNumberAction(),
                    ActionProvider.provideSubstractImagesAction());
            return imagesOperationsPresenter;
        }
        return imagesOperationsPresenter;
    }

    public static ExponentialScenePresenter provideExponentialScenePresenter(ExponentialSceneController exponentialSceneController) {
        return new ExponentialScenePresenter(exponentialSceneController,
                ServiceProvider.provideRandomNumberGenerationService(),
                ActionProvider.provideGenerateSyntheticNoiseImageAction(),
                PublishSubjectProvider.provideOnNoiseImagePublishSubject());
    }

    public static RayleighScenePresenter provideRayleighScenePresenter(RayleighSceneController rayleighSceneController) {
        return new RayleighScenePresenter(rayleighSceneController,
                ServiceProvider.provideRandomNumberGenerationService(),
                ActionProvider.provideGenerateSyntheticNoiseImageAction(),
                PublishSubjectProvider.provideOnNoiseImagePublishSubject());
    }

    public static GaussianScenePresenter provideGaussianScenePresenter(GaussianSceneController gaussianSceneController) {
        return new GaussianScenePresenter(gaussianSceneController,
                ServiceProvider.provideRandomNumberGenerationService(),
                ActionProvider.provideGenerateSyntheticNoiseImageAction(),
                PublishSubjectProvider.provideOnNoiseImagePublishSubject());
    }

    public static SaltAndPepperNoisePresenter provideSaltAndPepperNoisePresenter(SaltAndPepperNoiseController saltAndPepperNoiseController) {
        return new SaltAndPepperNoisePresenter(saltAndPepperNoiseController,
                ActionProvider.provideGetImageAction(),
                ActionProvider.provideApplySaltAndPepperNoiseAction());
    }

    public static MediaFilterPresenter provideMediaFilterPresenter(MediaFilterSceneController mediaFilterSceneController) {
        return new MediaFilterPresenter(mediaFilterSceneController,
                ActionProvider.provideGetImageAction(),
                ActionProvider.provideApplyFilterAction());
    }

    public static NoiseImagePresenter provideNoiseImagePresenter(NoiseImageSceneController noiseImageSceneController) {
        return new NoiseImagePresenter(noiseImageSceneController,
                PublishSubjectProvider.provideOnNoiseImagePublishSubject(),
                ActionProvider.provideCreateImageHistogram());
    }

    public static EqualizedImagePresenter provideEqualizedImagePresenter(EqualizedImageSceneController equalizedImageSceneController) {
        return new EqualizedImagePresenter(equalizedImageSceneController,
                ActionProvider.provideGetImageAction(),
                ActionProvider.provideCreateEqualizedGrayImageAction(
                        PublishSubjectProvider.provideOnEqualizeImagePublishSubject()),
                ActionProvider.provideCreateImageHistogram(),
                PublishSubjectProvider.provideOnEqualizeImagePublishSubject());
    }
}
