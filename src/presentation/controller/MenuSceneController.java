package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import presentation.presenter.ImageSelectionPresenter;
import presentation.scenecreator.ImageSceneCreator;

public class MenuSceneController {

    private final ImageSelectionPresenter imageSelectionPresenter;

    public MenuSceneController() {
        imageSelectionPresenter = PresenterProvider.provideImageSelectionPresenter();
    }

    @FXML
    private void showImageScene(ActionEvent event) {
        new ImageSceneCreator(imageSelectionPresenter.getImage("image1")).createScene();
    }

    @FXML
    private void saveImage(ActionEvent event) {
        imageSelectionPresenter.saveImage("image1", "IMAGE 1");
    }

    @FXML
    private void onClickButtonTP1(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP2(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP3(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP4(ActionEvent event) {

    }

}
