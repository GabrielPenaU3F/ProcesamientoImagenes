package presentation.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowResultPopup {

    public static void show(String title, String message) {

            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle(title);

            Label messageLabel = new Label(message);
            messageLabel.setMaxWidth(250);
            messageLabel.setAlignment(Pos.CENTER);
            Button closeButton = new Button("Accept");
            closeButton.setOnAction(e -> popupWindow.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(messageLabel, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene popupScene = new Scene(layout, 400, 100);
            popupWindow.setScene(popupScene);
            popupWindow.showAndWait();

    }
}
