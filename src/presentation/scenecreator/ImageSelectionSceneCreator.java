package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageSelectionSceneCreator implements SceneCreator {

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/image_selection_scene_view.fxml"));
            Scene imageScene = new Scene(root);
            Stage imageStage = new Stage(StageStyle.DECORATED);
            imageStage.setTitle("Seleccion de Imagen");
            imageStage.setScene(imageScene);
            imageStage.show();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create new scene");
        }
    }
}
