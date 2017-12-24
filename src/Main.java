import javafx.application.Application;
import javafx.stage.Stage;
import presentation.scenecreator.MenuSceneCreator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MenuSceneCreator(primaryStage).createScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
