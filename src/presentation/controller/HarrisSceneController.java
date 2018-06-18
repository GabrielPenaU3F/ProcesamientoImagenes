package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import presentation.presenter.HarrisPresenter;

public class HarrisSceneController {

    @FXML
    public ImageView imageView1;

    @FXML
    public ImageView imageView2;

    @FXML
    public Label resultLabel;

    private HarrisPresenter harrisPresenter;

    public HarrisSceneController() {
        this.harrisPresenter = PresenterProvider.provideHarrisPresenter(this);
    }


    @FXML
    public void onApply() {
        this.harrisPresenter.onApply();
    }

    @FXML
    public void onSelectImage1() {
        this.harrisPresenter.onSelectImage1();
    }

    @FXML
    public void onSelectImage2() {
        this.harrisPresenter.onSelectImage2();
    }



}
