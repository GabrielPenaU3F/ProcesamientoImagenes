package presentation.controller;

import core.provider.PresenterProvider;
import presentation.presenter.ImagesSumPresenter;

public class ImagesSumSceneController {

    private final ImagesSumPresenter imagesSumPresenter;

    public ImagesSumSceneController(){
        this.imagesSumPresenter = PresenterProvider.provideImagesSumPresenter();
    }

    public void makeImagesSum(){
        this.imagesSumPresenter.onMakeImagesSum();
    }

    public void loadImage1(){
        this.imagesSumPresenter.onloadImage1();
    }

    public void loadImage2(){
        this.imagesSumPresenter.onloadImage2();
    }
}
