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
            Scene colorGradientScene = new Scene(root);
            Stage colorGradientStage = new Stage(StageStyle.DECORATED);
            colorGradientStage.setTitle("Degradé de colores");
            colorGradientStage.setScene(colorGradientScene);
            colorGradientStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
