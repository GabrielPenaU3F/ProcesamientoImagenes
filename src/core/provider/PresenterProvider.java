package core.provider;

import presentation.presenter.ImageInformPresenter;
import presentation.presenter.MenuPresenter;
import presentation.presenter.ColorGradientPresenter;
import presentation.presenter.GreyGradientPresenter;
import presentation.presenter.ImageViewPresenter;

public class PresenterProvider {

    private static MenuPresenter menuPresenter;
    private static ImageViewPresenter imageViewPresenter;
    private static ImageInformPresenter imageInformPresenter;
    private static GreyGradientPresenter greyGradientPresenter;
    private static ColorGradientPresenter colorGradientPresenter;

    public static MenuPresenter provideImageSelectionPresenter() {
        if (menuPresenter == null) {
            menuPresenter = new MenuPresenter(
                    ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideSetCurrentImagePathOnRepoAction(),
                    ActionProvider.provideGetCurrentImagePathAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideSaveImageAction(),
                    ActionProvider.provideGetImageListAction()
            );
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

    public static GreyGradientPresenter provideGreyGradientPresenter() {
        if (greyGradientPresenter == null) {
            greyGradientPresenter = new GreyGradientPresenter();
            return greyGradientPresenter;
        }
        return greyGradientPresenter;
    }

    public static ColorGradientPresenter provideColorGradientPresenter() {
        if (colorGradientPresenter == null) {
            colorGradientPresenter = new ColorGradientPresenter();
            return colorGradientPresenter;
        }
        return colorGradientPresenter;
    }
}
