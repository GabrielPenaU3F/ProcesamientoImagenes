package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneCreator implements SceneCreator{

    private final Stage stage;

    public MainSceneCreator(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main_scene_view.fxml"));
            this.stage.setTitle("Procesamiento de Imagenes - Untref 2018");
            this.stage.setScene(new Scene(root, 800, 600));
            this.stage.setMaximized(true);
            this.stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
