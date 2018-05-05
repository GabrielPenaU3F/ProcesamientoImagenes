package core.provider;

import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import core.action.edgedetector.ApplyDirectionalDerivativeOperatorAction;
import core.action.edit.ModifyPixelAction;
import core.action.edit.space_domain.*;
import core.action.edit.space_domain.operations.MultiplyImageByScalarNumberAction;
import core.action.edit.space_domain.operations.MultiplyImagesAction;
import core.action.edit.space_domain.operations.SubstractImagesAction;
import core.action.edit.space_domain.operations.SumImagesAction;
import core.action.figure.CreateImageWithFigureAction;
import core.action.filter.ApplyFilterAction;
import core.action.edgedetector.ApplyEdgeDetectorByGradientAction;
import core.action.gradient.CreateImageWithGradientAction;
import core.action.histogram.CreateImageHistogramAction;
import core.action.histogram.EqualizeGrayImageAction;
import core.action.image.*;
import core.action.noise.ApplyExponentialNoiseToImageAction;
import core.action.noise.ApplyGaussianNoiseToImageAction;
import core.action.noise.ApplyRayleighNoiseToImageAction;
import core.action.noise.ApplySaltAndPepperNoiseAction;
import core.action.noise.generator.GenerateSyntheticNoiseImageAction;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

class ActionProvider {

    private static LoadImageAction loadImageAction;
    private static GetImageAction getImageAction;
    private static SaveImageAction saveImageAction;
    private static ModifyPixelAction modifyPixelAction;
    private static PutModifiedImageAction putModifiedImageAction;
    private static GetModifiedImageAction getModifiedImageAction;
    private static CreateImageInformAction createImageInformAction;
    private static ObtainRGBChannelAction obtainRGBChannelAction;
    private static ObtainHSVChannelAction obtainHSVChannelAction;
    private static CreateImageWithFigureAction createImageWithFigureAction;
    private static CreateImageWithGradientAction createImageWithGradientAction;
    private static CalculateNegativeImageAction calculateNegativeImageAction;
    private static ApplyThresholdAction applyThresholdAction;
    private static CreateImageHistogramAction createImageHistogramAction;
    private static ApplyContrastAction applyContrastAction;
    private static EqualizeGrayImageAction createEqualizeGrayImageAction;
    private static CompressDynamicRangeAction compressDynamicRangeAction;
    private static GammaFunctionAction gammaFunctionAction;
    private static MultiplyImagesAction multiplyImagesAction;
    private static SumImagesAction sumImagesAction;
    private static NormalizeImageAction normalizeImageAction;
    private static MultiplyImageByScalarNumberAction multiplyImageByScalarNumberAction;
    private static SubstractImagesAction substractImagesAction;
    private static ApplySaltAndPepperNoiseAction applySaltAndPepperAction;
    private static ApplyFilterAction applyFilterAction;
    private static GenerateSyntheticNoiseImageAction generateSyntheticNoiseImageAction;
    private static ApplyGaussianNoiseToImageAction applyGaussianNoiseToImageAction;
    private static ApplyRayleighNoiseToImageAction applyRayleighNoiseToImageAction;
    private static ApplyExponentialNoiseToImageAction applyExponentialNoiseToImageAction;
    private static ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction;
    private static UpdateCurrentImageAction updateCurrentImageAction;
    private static ApplyDirectionalDerivativeOperatorAction applyDirectionalDerivativeOperatorAction;

    public static GetImageAction provideGetImageAction() {
        if (getImageAction == null) {
            getImageAction = new GetImageAction(RepositoryProvider.provideImageRepository());
        }
        return getImageAction;
    }

    public static LoadImageAction provideLoadImageAction() {
        if (loadImageAction == null) {
            loadImageAction = new LoadImageAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideOpenFileService(),
                    CommonProvider.provideOpener(),
                    ServiceProvider.provideImageRawService());
        }
        return loadImageAction;
    }

    public static SaveImageAction provideSaveImageAction() {
        if (saveImageAction == null) {
            saveImageAction = new SaveImageAction(ServiceProvider.provideImageRawService());
        }
        return saveImageAction;
    }

    public static ModifyPixelAction provideModifyPixelAction() {
        if (modifyPixelAction == null) {
            modifyPixelAction = new ModifyPixelAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideModifyImageService()
            );
        }
        return modifyPixelAction;
    }

    public static PutModifiedImageAction providePutModifiedImageAction() {
        if (putModifiedImageAction == null) {
            putModifiedImageAction = new PutModifiedImageAction(RepositoryProvider.provideImageRepository());
        }
        return putModifiedImageAction;
    }

    public static GetModifiedImageAction provideGetModifiedImageAction() {
        if (getModifiedImageAction == null) {
            getModifiedImageAction = new GetModifiedImageAction(RepositoryProvider.provideImageRepository());
        }
        return getModifiedImageAction;
    }

    public static ObtainRGBChannelAction provideObtainRGBChannelAction() {
        if (obtainRGBChannelAction == null) {
            obtainRGBChannelAction = new ObtainRGBChannelAction(RepositoryProvider.provideImageRepository());
            return obtainRGBChannelAction;
        }
        return obtainRGBChannelAction;
    }

    public static CreateImageInformAction provideCreateImageInformAction() {
        if (createImageInformAction == null) {
            createImageInformAction = new CreateImageInformAction();
        }
        return createImageInformAction;
    }

    public static ObtainHSVChannelAction provideObtainHSVChannelAction() {
        if (obtainHSVChannelAction == null) {
            obtainHSVChannelAction = new ObtainHSVChannelAction(RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideTransformRGBtoHSVImageService());
        }
        return obtainHSVChannelAction;
    }

    public static CreateImageWithFigureAction provideCreateImageWithFigureAction() {
        if (createImageWithFigureAction == null) {
            createImageWithFigureAction = new CreateImageWithFigureAction(
                    ServiceProvider.provideImageFigureService(),
                    RepositoryProvider.provideImageRepository());
        }
        return createImageWithFigureAction;
    }

    public static CreateImageWithGradientAction provideCreateGradientAction() {
        if (createImageWithGradientAction == null) {
            createImageWithGradientAction = new CreateImageWithGradientAction(
                    ServiceProvider.provideGradientService(),
                    RepositoryProvider.provideImageRepository());
        }
        return createImageWithGradientAction;
    }

    public static CalculateNegativeImageAction provideCalculateNegativeImageAction() {
        if (calculateNegativeImageAction == null) {
            calculateNegativeImageAction = new CalculateNegativeImageAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideModifyImageService());
        }
        return calculateNegativeImageAction;
    }

    public static ApplyThresholdAction provideApplyThresholdAction() {
        if (applyThresholdAction == null) {
            applyThresholdAction = new ApplyThresholdAction(ServiceProvider.provideModifyImageService());
        }
        return applyThresholdAction;
    }

    public static CreateImageHistogramAction provideCreateImageHistogram() {
        if (createImageHistogramAction == null) {
            createImageHistogramAction = new CreateImageHistogramAction(ServiceProvider.provideHistogramService());
        }
        return createImageHistogramAction;
    }

    public static ApplyContrastAction provideApplyContrastAction() {
        if (applyContrastAction == null) {
            applyContrastAction = new ApplyContrastAction(
                    ServiceProvider.provideModifyImageService());
        }
        return applyContrastAction;
    }

    public static EqualizeGrayImageAction provideCreateEqualizedGrayImageAction(PublishSubject<Image> imagePublishSubject) {
        return new EqualizeGrayImageAction(
                ServiceProvider.provideHistogramService(),
                RepositoryProvider.provideImageRepository(),
                imagePublishSubject);
    }

    public static CompressDynamicRangeAction provideCompressDynamicRangeAction() {
        if (compressDynamicRangeAction == null) {
            compressDynamicRangeAction = new CompressDynamicRangeAction(
                    ServiceProvider.provideGrayLevelStatisticsService(),
                    RepositoryProvider.provideImageRepository());
        }
        return compressDynamicRangeAction;
    }

    public static GammaFunctionAction provideGammaFunctionAction() {
        if (gammaFunctionAction == null) {
            gammaFunctionAction = new GammaFunctionAction(RepositoryProvider.provideImageRepository());
        }
        return gammaFunctionAction;
    }

    public static SumImagesAction provideSumImagesAction() {
        if (sumImagesAction == null) {
            sumImagesAction = new SumImagesAction(ServiceProvider.provideImageOperationsService());
        }
        return sumImagesAction;
    }

    public static MultiplyImagesAction provideMultiplyImagesAction() {
        if (multiplyImagesAction == null) {
            multiplyImagesAction = new MultiplyImagesAction(ServiceProvider.provideImageOperationsService());
        }
        return multiplyImagesAction;
    }

    public static SubstractImagesAction provideSubstractImagesAction() {
        if (substractImagesAction == null) {
            substractImagesAction = new SubstractImagesAction(ServiceProvider.provideImageOperationsService());
        }
        return substractImagesAction;
    }

    public static NormalizeImageAction provideNormalizeImageAction() {
        if (normalizeImageAction == null) {
            normalizeImageAction = new NormalizeImageAction(ServiceProvider.provideImageOperationsService());
        }
        return normalizeImageAction;
    }

    public static MultiplyImageByScalarNumberAction provideMultiplyImageWithScalarNumberAction() {
        if (multiplyImageByScalarNumberAction == null) {
            multiplyImageByScalarNumberAction = new MultiplyImageByScalarNumberAction(ServiceProvider.provideImageOperationsService(),
                    ActionProvider.provideCompressDynamicRangeAction());
        }
        return multiplyImageByScalarNumberAction;
    }

    public static ApplySaltAndPepperNoiseAction provideApplySaltAndPepperNoiseAction() {
        if (applySaltAndPepperAction == null) {
            applySaltAndPepperAction = new ApplySaltAndPepperNoiseAction(
                    ServiceProvider.provideRandomNumberGenerationService(),
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject());
        }
        return applySaltAndPepperAction;
    }

    public static ApplyFilterAction provideApplyFilterAction() {
        if (applyFilterAction == null) {
            applyFilterAction = new ApplyFilterAction(
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject(),
                    ServiceProvider.provideImageOperationsService()
            );
        }
        return applyFilterAction;
    }

    public static GenerateSyntheticNoiseImageAction provideGenerateSyntheticNoiseImageAction() {
        if (generateSyntheticNoiseImageAction == null) {
            generateSyntheticNoiseImageAction = new GenerateSyntheticNoiseImageAction(
                    ServiceProvider.provideImageOperationsService());
        }
        return generateSyntheticNoiseImageAction;
    }

    public static ApplyGaussianNoiseToImageAction provideApplyGaussianNoiseToImageAction() {
        if (applyGaussianNoiseToImageAction == null) {
            applyGaussianNoiseToImageAction = new ApplyGaussianNoiseToImageAction(RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideImageOperationsService(),
                    ServiceProvider.provideRandomNumberGenerationService());
        }
        return applyGaussianNoiseToImageAction;
    }

    public static ApplyRayleighNoiseToImageAction provideApplyRayleighNoiseToImageAction() {
        if (applyRayleighNoiseToImageAction == null) {
            applyRayleighNoiseToImageAction = new ApplyRayleighNoiseToImageAction(RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideImageOperationsService(),
                    ServiceProvider.provideRandomNumberGenerationService());
        }
        return applyRayleighNoiseToImageAction;
    }

    public static ApplyExponentialNoiseToImageAction provideApplyExponentialNoiseToImageAction() {
        if (applyExponentialNoiseToImageAction == null) {
            applyExponentialNoiseToImageAction = new ApplyExponentialNoiseToImageAction(RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideImageOperationsService(),
                    ServiceProvider.provideRandomNumberGenerationService());
        }
        return applyExponentialNoiseToImageAction;
    }

    public static ApplyEdgeDetectorByGradientAction provideApplyEdgeDetectorByGradient() {
        if (applyEdgeDetectorByGradientAction == null) {
            applyEdgeDetectorByGradientAction = new ApplyEdgeDetectorByGradientAction(
                    ServiceProvider.provideImageOperationsService(),
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject(),
                    ServiceProvider.provideMatrixService()
            );
        }
        return applyEdgeDetectorByGradientAction;
    }

    public static UpdateCurrentImageAction provideUpdateCurrentImageAction() {
        if (updateCurrentImageAction == null) {
            updateCurrentImageAction = new UpdateCurrentImageAction(RepositoryProvider.provideImageRepository());
        }
        return updateCurrentImageAction;
    }

    public static ApplyDirectionalDerivativeOperatorAction provideApplyDerivateDirectionalOperatorAction() {
        if (applyDirectionalDerivativeOperatorAction == null) {
            applyDirectionalDerivativeOperatorAction = new ApplyDirectionalDerivativeOperatorAction(
                    ServiceProvider.provideImageOperationsService(),
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject(),
                    ServiceProvider.provideMatrixService()
            );
        }
        return applyDirectionalDerivativeOperatorAction;
    }
}
