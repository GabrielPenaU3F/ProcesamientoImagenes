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
    public ImageView image1;

    @FXML
    public ImageView image2;


    public ImagesOperationsSceneController(){
        this.imagesOperationsPresenter = PresenterProvider.provideImagesOperationPresenter();
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
        image1.setImage(this.imagesOperationsPresenter.onloadImage1());
    }

    public void loadImage2(){
        image2.setImage(this.imagesOperationsPresenter.onloadImage2());
    }
}
