package presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import presentation.scenecreator.ImageSelectionSceneCreator;

public class MenuSceneController {

    @FXML
    private void onClickButtonTP1(ActionEvent event) {
        new ImageSelectionSceneCreator().createScene();
    }

    @FXML
    public void onClickButtonTP2(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP3(ActionEvent event) {

    }
}
