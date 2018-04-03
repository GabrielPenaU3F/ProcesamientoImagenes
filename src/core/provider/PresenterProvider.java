package core.provider;

import presentation.controller.ImageHistogramSceneController;
import presentation.controller.MainSceneController;
import presentation.controller.SaveImageController;
import presentation.presenter.ImageHistogramPresenter;
import presentation.presenter.ImageReportPresenter;
import presentation.presenter.MainPresenter;
import presentation.presenter.SaveImagePresenter;

public class PresenterProvider {

    private static MainPresenter mainPresenter;

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
}
