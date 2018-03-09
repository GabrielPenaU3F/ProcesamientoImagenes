package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ColorGradientSceneCreator implements SceneCreator {

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/color_gradient_scene_view.fxml"));
            Scene greyGradientScene = new Scene(root);
            Stage greyGradientStage = new Stage(StageStyle.DECORATED);
            greyGradientStage.setTitle("Degradé de colores");
            greyGradientStage.setScene(greyGradientScene);
            greyGradientStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
