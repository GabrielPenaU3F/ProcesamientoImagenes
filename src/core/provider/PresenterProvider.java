package core.provider;

import presentation.controller.MainSceneController;
import presentation.presenter.*;

public class PresenterProvider {

    private static MainPresenter mainPresenter;
    private static ImageReportPresenter imageReportPresenter;
    private static ImageGradientPresenter imageGradientPresenter;
    private static ImageFigurePresenter imageFigurePresenter;
    private static ChannelScenePresenter channelScenePresenter;
    private static ImagesOperationsPresenter imagesOperationsPresenter;

    public static MainPresenter provideImageSelectionPresenter(MainSceneController mainSceneController) {
        if (mainPresenter == null) {
            mainPresenter = new MainPresenter(
                    mainSceneController,
                    ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideGetModifiedImageAction(),
                    ActionProvider.providePutModifiedImageAction(),
                    ActionProvider.provideModifyPixelAction(),
                    ActionProvider.provideSaveImageAction(),
                    ActionProvider.provideCalculateNegativeImageAction(),
                    ActionProvider.provideApplyThresholdAction());
            return mainPresenter;
        }
        return mainPresenter;
    }

    public static ImageReportPresenter provideImageInformPresenter() {
        if (imageReportPresenter == null) {
            imageReportPresenter = new ImageReportPresenter(
                    ActionProvider.provideGetModifiedImageAction(),
                    ActionProvider.provideCreateImageInformAction()
            );
        }
        return imageReportPresenter;
    }

    public static ImageGradientPresenter provideImageGradientPresenter() {
        if (imageGradientPresenter == null) {
            imageGradientPresenter = new ImageGradientPresenter(ActionProvider.provideCreateGradientAction());
            return imageGradientPresenter;
        }
        return imageGradientPresenter;
    }

    public static ImageFigurePresenter provideImageFigurePresenter() {
        if (imageFigurePresenter == null) {
            imageFigurePresenter = new ImageFigurePresenter(
                    ActionProvider.provideSaveImageAction(),
                    ActionProvider.provideCreateImageWithFigureAction());
            return imageFigurePresenter;
        }
        return imageFigurePresenter;
    }

    public static ChannelScenePresenter provideChannelScenePresenter() {
        if (channelScenePresenter == null) {
            channelScenePresenter = new ChannelScenePresenter(ActionProvider.provideObtainRGBChannelAction(), ActionProvider.provideObtainHSVChannelAction());
            return channelScenePresenter;
        }
        return channelScenePresenter;
    }

    public static ImagesOperationsPresenter provideImagesSumPresenter() {
        if (imagesOperationsPresenter == null) {
            imagesOperationsPresenter = new ImagesOperationsPresenter(ActionProvider.provideLoadImageAction());
            return imagesOperationsPresenter;
        }
        return imagesOperationsPresenter;
    }
}
