package presentation.scenecreator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuSceneCreator implements SceneCreator{

    private Stage stage;

    public MenuSceneCreator(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void createScene() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu_scene_view.fxml"));
            this.stage.setTitle("Procesador de Imagenes - Untref 2018");
            this.stage.setScene(new Scene(root, 800, 600));
            this.stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
