package core.provider;

import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.ContrastSceneController;
import presentation.controller.ImageHistogramSceneController;
import presentation.controller.MainSceneController;
import presentation.controller.SaveImageController;
import presentation.presenter.*;

public class PresenterProvider {

    private static MainPresenter mainPresenter;
    private static ImageReportPresenter imageReportPresenter;
    private static ImageGradientPresenter imageGradientPresenter;
    private static ImageFigurePresenter imageFigurePresenter;
    private static ChannelScenePresenter channelScenePresenter;
    private static SaveImagePresenter saveImagePresenter;
    private static ImageHistogramPresenter imageHistogramPresenter;
    private static PublishSubject<Image> onModifiedImagePublishSubject;

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
                    ActionProvider.provideApplyThresholdAction(),
                    PublishSubjectProvider.provideOnModifiedImagePublishSubject());
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
            channelScenePresenter = new ChannelScenePresenter(
                    ActionProvider.provideObtainRGBChannelAction(),
                    ActionProvider.provideObtainHSVChannelAction());
            return channelScenePresenter;
        }
        return channelScenePresenter;
    }

    public static SaveImagePresenter provideSaveImagePresenter(SaveImageController saveImageController) {
        if (saveImagePresenter == null) {
            saveImagePresenter = new SaveImagePresenter(saveImageController, ActionProvider.provideGetModifiedImageAction(), ActionProvider.provideSaveImageAction());
            return saveImagePresenter;
        }
        return saveImagePresenter;
    }

    public static ImageHistogramPresenter provideImageHistogramPresenter(ImageHistogramSceneController view) {
        if (imageHistogramPresenter == null) {
            imageHistogramPresenter = new ImageHistogramPresenter(view,
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideCreateImageHistogram(),
                    ActionProvider.provideCreateGradientAction());
            return imageHistogramPresenter;
        }
        return imageHistogramPresenter;
    }

    public static ContrastScenePresenter provideContrastScenePresenter(ContrastSceneController contrastSceneController) {
        return new ContrastScenePresenter(contrastSceneController,
                ActionProvider.provideApplyContrastAction(),
                ActionProvider.provideGetImageAction(),
                ServiceProvider.provideGrayLevelStatisticsService(),
                PublishSubjectProvider.provideOnModifiedImagePublishSubject());
    }
}
