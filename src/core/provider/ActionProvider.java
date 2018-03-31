package core.provider;

import core.action.UpdateMainViewAction;
import core.action.histogram.CreateImageHistogramAction;
import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import core.action.edit.ModifyPixelAction;
import core.action.edit.space_domain.ApplyContrastAction;
import core.action.edit.space_domain.ApplyThresholdAction;
import core.action.edit.space_domain.CalculateNegativeImageAction;
import core.action.figure.CreateImageWithFigureAction;
import core.action.gradient.CreateImageWithGradientAction;
import core.action.histogram.EqualizeGrayImageAction;
import core.action.image.CreateImageInformAction;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.SaveImageAction;
import core.action.modifiedimage.GetModifiedImageAction;
import core.action.modifiedimage.PutModifiedImageAction;

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
    private static UpdateMainViewAction updateMainViewAction;
    private static EqualizeGrayImageAction createEqualizeGrayImageAction;

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
            applyThresholdAction = new ApplyThresholdAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideModifyImageService());
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
            applyContrastAction = new ApplyContrastAction(RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideModifyImageService());
        }
        return applyContrastAction;
    }

    public static UpdateMainViewAction provideUpdateMainViewAction() {
        if (updateMainViewAction == null) {
            updateMainViewAction = new UpdateMainViewAction(PresenterProvider.provideImageSelectionPresenter(ViewProvider.provideMainView()));
        }
        return updateMainViewAction;
    }

    public static EqualizeGrayImageAction provideCreateEqualizedGrayImageAction() {
        if (createEqualizeGrayImageAction == null) {
            createEqualizeGrayImageAction = new EqualizeGrayImageAction(
                    ServiceProvider.provideHistogramService(),
                    RepositoryProvider.provideImageRepository());
        }
        return createEqualizeGrayImageAction;
    }
}
