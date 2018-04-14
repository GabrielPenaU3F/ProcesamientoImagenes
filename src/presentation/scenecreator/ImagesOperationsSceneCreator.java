package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImagesOperationsSceneCreator implements SceneCreator {

    @Override
    public void createScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/images_operations_scene_view.fxml"));
            Scene imageScene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setMaximized(true);
            stage.setTitle("Image Operations");
            stage.setScene(imageScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
