package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import presentation.presenter.ImagesSumPresenter;

public class ImagesSumSceneController {

    private final ImagesSumPresenter imagesSumPresenter;

    @FXML
    public ImageView imageView;

    @FXML
    public Group groupImageView;

    public ImagesSumSceneController(){
        this.imagesSumPresenter = PresenterProvider.provideImagesSumPresenter();
    }

    public void makeImagesSum(){
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imagesSumPresenter.onMakeImagesSum());
    }

    public void loadImage1(){
        this.imagesSumPresenter.onloadImage1();
    }

    public void loadImage2(){
        this.imagesSumPresenter.onloadImage2();
    }
}
