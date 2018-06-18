package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import presentation.presenter.SiftPresenter;

public class SiftSceneController {

    @FXML
    public ImageView imageView1;

    @FXML
    public ImageView imageView2;

    private SiftPresenter siftPresenter;

    public SiftSceneController() {
        this.siftPresenter = PresenterProvider.provideSiftPresenter(this);
    }


    @FXML
    public void onApply() {
        this.siftPresenter.onApply();
    }

    @FXML
    public void onSelectImage1() {
        this.siftPresenter.onSelectImage1();
    }

    @FXML
    public void onSelectImage2() {
        this.siftPresenter.onSelectImage2();
    }
}
