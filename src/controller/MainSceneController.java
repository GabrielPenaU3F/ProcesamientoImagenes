package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainSceneController {

    @FXML
    private void showImageScene(ActionEvent event) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../view/image_scene_view.fxml"));
        Scene imageScene = new Scene(root);
        Stage imageStage = new Stage(StageStyle.DECORATED);
        imageStage.setTitle("Imagen Cargada");
        imageStage.setScene(imageScene);
        imageStage.show();


    }

}
