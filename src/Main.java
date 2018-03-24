import javafx.application.Application;
import javafx.stage.Stage;
import presentation.scenecreator.MainSceneCreator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new MainSceneCreator(primaryStage).createScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
