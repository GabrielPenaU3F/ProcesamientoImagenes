package presentation.controller;

import core.provider.PresenterProvider;
import io.reactivex.functions.Consumer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentation.presenter.ImageSelectionPresenter;
import presentation.scenecreator.ImageViewSceneCreator;

import java.util.function.Supplier;

public class MenuSceneController {

    @FXML
    public ComboBox<String> imageComboBox;

    private final ImageSelectionPresenter imageSelectionPresenter;
    private static final String EMPTY = "";

    public MenuSceneController() {
        imageSelectionPresenter = PresenterProvider.provideImageSelectionPresenter();
    }

    @FXML
    private void loadImage(ActionEvent event) {

        String imageName = imageSelectionPresenter.loadImage(showInsertNamePopup());
        if (!imageName.equals(EMPTY)) {
            ObservableList<String> items = imageComboBox.getItems();
            items.add(imageName);
            imageComboBox.setItems(items);
        }
    }

    private Supplier<String> showInsertNamePopup() {

        return new Supplier<String>() {

            @Override
            public String get() {

                Stage popupWindow = new Stage();
                popupWindow.initModality(Modality.APPLICATION_MODAL);
                popupWindow.setTitle("Insertar nombre");

                TextField nameTextField = new TextField("default");
                nameTextField.setMaxWidth(100);
                Button closeButton = new Button("Aceptar");
                closeButton.setOnAction(e -> popupWindow.close());

                VBox layout = new VBox(10);
                layout.getChildren().addAll(nameTextField, closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene popupScene = new Scene(layout, 200, 100);
                popupWindow.setScene(popupScene);
                popupWindow.showAndWait();

                return nameTextField.getText();
            }
        };
    }

    @FXML
    public void saveImage(ActionEvent event) {

        imageSelectionPresenter.setCurrentImagePath(imageComboBox.getSelectionModel().getSelectedItem());
        imageSelectionPresenter.saveImage(showInsertNamePopup().get());
    }

    @FXML
    public void showImage(ActionEvent event) {
        imageSelectionPresenter.setCurrentImagePath(imageComboBox.getSelectionModel().getSelectedItem());
        new ImageViewSceneCreator().createScene();
    }

    @FXML
    private void onClickButtonTP1(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP2(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP3(ActionEvent event) {

    }

    @FXML
    public void onClickButtonTP4(ActionEvent event) {

    }

}
