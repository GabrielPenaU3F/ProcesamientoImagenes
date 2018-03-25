package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ImageFigureSceneCreator implements SceneCreator {

    @Override
    public void createScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/image_figure_scene_view.fxml"));
            Scene imageScene = new Scene(root);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Figure");
            stage.setScene(imageScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
