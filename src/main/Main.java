package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene_creator.MainSceneCreator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainSceneCreator mainSceneCreator = new MainSceneCreator(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
