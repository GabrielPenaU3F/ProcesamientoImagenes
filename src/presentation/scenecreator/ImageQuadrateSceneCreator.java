package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageQuadrateSceneCreator implements SceneCreator {

    @Override
    public void createScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/image_quadrate_scene_view.fxml"));
            Scene quadrateImageScene = new Scene(root);
            Stage quadrateImageSceneStage = new Stage(StageStyle.DECORATED);
            quadrateImageSceneStage.setTitle("Imagen con rectangulo");
            quadrateImageSceneStage.setScene(quadrateImageScene);
            quadrateImageSceneStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
