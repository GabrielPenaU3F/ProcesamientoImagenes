package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import presentation.presenter.ImagesOperationsPresenter;

public class ImagesOperationsSceneController {

    private final ImagesOperationsPresenter imagesOperationsPresenter;

    @FXML
    public ImageView imageView;

    @FXML
    public Group groupImageView;

    public ImagesOperationsSceneController(){
        this.imagesOperationsPresenter = PresenterProvider.provideImagesSumPresenter();
    }

    public void makeImagesSum(){
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imagesOperationsPresenter.onMakeImagesSum());
    }

    public void makeImagesMultiplication(){
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imagesOperationsPresenter.onMakeImagesMultiplication());
    }

    public void loadImage1(){
        this.imagesOperationsPresenter.onloadImage1();
    }

    public void loadImage2(){
        this.imagesOperationsPresenter.onloadImage2();
    }
}
