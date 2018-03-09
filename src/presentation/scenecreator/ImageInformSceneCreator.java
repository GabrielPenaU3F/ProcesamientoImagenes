package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageInformSceneCreator implements SceneCreator {

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/image_inform_scene_view.fxml"));
            Scene imageScene = new Scene(root);
            Stage imageStage = new Stage(StageStyle.DECORATED);
            imageStage.setScene(imageScene);
            imageStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
