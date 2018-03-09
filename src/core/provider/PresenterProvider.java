package core.provider;

import presentation.presenter.ColorGradientPresenter;
import presentation.presenter.GreyGradientPresenter;
import presentation.presenter.ImageSelectionPresenter;
import presentation.presenter.ImageViewPresenter;

public class PresenterProvider {

    private static ImageSelectionPresenter imageSelectionPresenter;
    private static ImageViewPresenter imageViewPresenter;
    private static GreyGradientPresenter greyGradientPresenter;
    private static ColorGradientPresenter colorGradientPresenter;

    public static ImageSelectionPresenter provideImageSelectionPresenter() {
        if (imageSelectionPresenter == null) {
            imageSelectionPresenter = new ImageSelectionPresenter(
                    ActionProvider.provideLoadImageAction(),
                    ActionProvider.provideSetCurrentImagePathOnRepoAction(),
                    ActionProvider.provideGetCurrentImagePathAction(),
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideSaveImageAction(),
                    ActionProvider.provideGetImageListAction());
            return imageSelectionPresenter;
        }
        return imageSelectionPresenter;
    }

    public static ImageViewPresenter provideImageViewPresenter() {
        if (imageViewPresenter == null) {
            imageViewPresenter = new ImageViewPresenter(
                    ActionProvider.provideGetImageAction(),
                    ActionProvider.provideModifyPixelAction(),
                    ActionProvider.provideLoadModifiedImageAction(),
                    ActionProvider.providePutModifiedImageAction());
            return imageViewPresenter;
        }
        return imageViewPresenter;
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
