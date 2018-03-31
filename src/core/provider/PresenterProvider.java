package core.provider;

import presentation.controller.ImageHistogramSceneController;
import presentation.controller.ContrastSceneController;
import presentation.controller.MainSceneController;
import presentation.controller.SaveImageController;
import presentation.presenter.*;

public class PresenterProvider {

    private static MainPresenter mainPresenter;
    private static ContrastScenePresenter contrastScenePresenter;

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
                    ActionProvider.provideCreateEqualizedGrayImageAction());

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
        return new SaveImagePresenter(saveImageController, ActionProvider.provideGetModifiedImageAction(), ActionProvider.provideSaveImageAction());

    }

    public static ImageHistogramPresenter provideImageHistogramPresenter(ImageHistogramSceneController view) {
        return new ImageHistogramPresenter(view,
                ActionProvider.provideGetImageAction(),
                ActionProvider.provideCreateImageHistogram());
    }

    public static ContrastScenePresenter provideContrastScenePresenter(ContrastSceneController contrastSceneController) {
        if (contrastScenePresenter == null) {
            contrastScenePresenter = new ContrastScenePresenter(contrastSceneController,
                    ActionProvider.provideApplyContrastAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideUpdateMainViewAction(),
                    ServiceProvider.provideGrayLevelStatisticsService());
            return contrastScenePresenter;
        }
        return contrastScenePresenter;
    }

}
