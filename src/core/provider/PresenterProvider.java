package core.provider;

import presentation.presenter.ImageSelectionPresenter;

public class PresenterProvider {

    private static ImageSelectionPresenter imageSelectionPresenter;

    public static ImageSelectionPresenter provideImageSelectionPresenter() {
        if (imageSelectionPresenter == null) {
            imageSelectionPresenter = new ImageSelectionPresenter(ActionProvider.provideGetImageAction(), ActionProvider.provideSaveImageAction());
            return imageSelectionPresenter;
        }
        return imageSelectionPresenter;
    }
}
