package scene_creator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageSceneCreator implements SceneCreator{

    public ImageSceneCreator() {

        this.createScene();

    }

    @Override
    public void createScene() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("../view/image_scene_view.fxml"));
            Scene imageScene = new Scene(root);
            Stage imageStage = new Stage(StageStyle.DECORATED);
            imageStage.setTitle("Imagen Cargada");
            imageStage.setScene(imageScene);
            imageStage.show();

        } catch (Exception e) {

            throw new RuntimeException("Failed to create new scene");

        }

    }
}
