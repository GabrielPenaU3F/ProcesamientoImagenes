package presentation.controller;

import core.provider.PresenterProvider;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import presentation.presenter.ImageSelectionPresenter;
import presentation.scenecreator.ImageViewSceneCreator;

public class MenuSceneController {

    private final ImageSelectionPresenter imageSelectionPresenter;
    @FXML public ComboBox<String> imageComboBox;
    @FXML private TextField fileNameInput;

    public MenuSceneController() {
        imageSelectionPresenter = PresenterProvider.provideImageSelectionPresenter();
    }

    @FXML
    private void loadImage(ActionEvent event) {
        String imagePath = imageSelectionPresenter.loadImage();
        ObservableList<String> items = imageComboBox.getItems();
        items.add(imagePath);
        imageComboBox.setItems(items);
    }

    @FXML
    public void saveImage(ActionEvent event) {

        imageSelectionPresenter.setCurrentImagePathOnRepo(imageComboBox.getSelectionModel().getSelectedItem());
        String fileName = fileNameInput.getText();
        if(fileName.equals("")) {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Error");

            Label popupLabel = new Label("Por favor ingrese un nombre para el archivo");
            Button closeButton = new Button("Cerrar");
            closeButton.setOnAction(e -> popupWindow.close());

            VBox layout= new VBox(10);
            layout.getChildren().addAll(popupLabel, closeButton);
            layout.setAlignment(Pos.CENTER);

            Scene popupScene = new Scene(layout, 250, 100);
            popupWindow.setScene(popupScene);
            popupWindow.showAndWait();

        } else {
            imageSelectionPresenter.saveImage(fileName);
        }
    }

    @FXML
    public void showImage(ActionEvent event) {
        imageSelectionPresenter.setCurrentImagePathOnRepo(imageComboBox.getSelectionModel().getSelectedItem());
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
