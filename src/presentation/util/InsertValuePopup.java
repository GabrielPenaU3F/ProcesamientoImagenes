package presentation.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Supplier;

public class InsertValuePopup {

    public static Supplier<String> show(String title, String defaultValue) {

        return new Supplier<String>() {

            @Override
            public String get() {

                Stage popupWindow = new Stage();
                popupWindow.initModality(Modality.APPLICATION_MODAL);
                popupWindow.setTitle(title);

                TextField nameTextField = new TextField(defaultValue);
                nameTextField.setMaxWidth(100);
                Button closeButton = new Button("Accept");
                closeButton.setOnAction(e -> popupWindow.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(nameTextField, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene popupScene = new Scene(layout, 250, 100);
                popupWindow.setScene(popupScene);
                popupWindow.showAndWait();

                return nameTextField.getText();
            }
        };
    }
}
