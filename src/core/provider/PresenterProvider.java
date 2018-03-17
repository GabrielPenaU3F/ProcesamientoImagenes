package core.provider;

import presentation.presenter.*;

public class PresenterProvider {

    private static MenuPresenter menuPresenter;
    private static ImageViewPresenter imageViewPresenter;
    private static ImageInformPresenter imageInformPresenter;
    private static ImageGradientPresenter imageGradientPresenter;
    private static ImageFigurePresenter imageFigurePresenter;
    private static ChannelScenePresenter channelScenePresenter;

    public static MenuPresenter provideImageSelectionPresenter() {
        if (menuPresenter == null) {
            menuPresenter = new MenuPresenter(
                    ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideSetCurrentImagePathOnRepoAction(),
                    ActionProvider.provideGetCurrentImagePathAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideSaveImageAction(),
                    ActionProvider.provideGetImageListAction());
            return menuPresenter;
        }
        return menuPresenter;
    }

    public static ImageViewPresenter provideImageViewPresenter() {
        if (imageViewPresenter == null) {
            imageViewPresenter = new ImageViewPresenter(
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideModifyPixelAction(),
                    ActionProvider.provideLoadModifiedImageAction(),
                    ActionProvider.providePutModifiedImageAction()
            );
        }
        return imageViewPresenter;
    }

    public static ImageInformPresenter provideImageInformPresenter() {
        if (imageInformPresenter == null) {
            imageInformPresenter = new ImageInformPresenter(
                    ActionProvider.provideGetModifiedImageAction(),
                    ActionProvider.provideCreateImageInformAction()
            );
        }
        return imageInformPresenter;
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
}
