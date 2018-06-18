package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HarrisSceneCreator implements SceneCreator {

    @Override
    public void createScene() {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/harris_scene_view.fxml"));
                Scene channelScene = new Scene(root);
                Stage channelStage = new Stage(StageStyle.DECORATED);
                channelStage.setTitle("Harris Corner Detection");
                channelStage.setScene(channelScene);
                channelStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

}
