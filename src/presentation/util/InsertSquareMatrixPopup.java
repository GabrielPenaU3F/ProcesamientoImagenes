package presentation.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InsertSquareMatrixPopup {

    public static Supplier<List<Integer>> show(String title, Integer size) {

        return new Supplier<List<Integer>>() {

            @Override
            public List<Integer> get() {

                Stage popupWindow = new Stage();
                popupWindow.initModality(Modality.APPLICATION_MODAL);
                popupWindow.setTitle(title);

                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        TextField textField = new TextField("1");
                        textField.setMaxWidth(30);
                        gridPane.add(textField, i, j);
                    }
                }

                Button closeButton = new Button("Accept");
                closeButton.setOnAction(e -> popupWindow.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(gridPane, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene popupScene = new Scene(layout, 250, 200);
                popupWindow.setScene(popupScene);
                popupWindow.showAndWait();

                return gridPane.getChildren()
                        .stream()
                        .map(node -> ((TextField) node).getText())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
        };
    }
}
