package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CannySceneCreator implements SceneCreator {

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/canny_scene_view.fxml"));
            Scene channelScene = new Scene(root);
            Stage channelStage = new Stage(StageStyle.DECORATED);
            channelStage.setTitle("Canny Edge Detector");
            channelStage.setScene(channelScene);
            channelStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
