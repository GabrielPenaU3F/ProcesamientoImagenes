package scene_creator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneCreator implements SceneCreator{

    private Stage stage;

    public MainSceneCreator(Stage stage) {

        this.stage = stage;
        this.createScene();

    }

    @Override
    public void createScene() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("../view/main_scene_view.fxml"));
            this.stage.setTitle("Procesador de Imagenes - Untref 2018");
            this.stage.setScene(new Scene(root, 700, 600));
            this.stage.show();

        } catch (Exception e) {

            throw new RuntimeException("Failed to create new scene");

        }

    }
}
