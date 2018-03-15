package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageCircleSceneCreator implements SceneCreator {

    @Override
    public void createScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/image_circle_scene_view.fxml"));
            Scene circleImageScene = new Scene(root);
            Stage circleImageSceneStage = new Stage(StageStyle.DECORATED);
            circleImageSceneStage.setTitle("Imagen con circulo");
            circleImageSceneStage.setScene(circleImageScene);
            circleImageSceneStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
