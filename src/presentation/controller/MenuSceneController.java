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
import javafx.stage.Stage;
import presentation.presenter.ImageSelectionPresenter;
import presentation.scenecreator.ImageViewSceneCreator;

public class MenuSceneController {

    public static final String EMPTY = "";
    @FXML
    public ComboBox<String> imageComboBox;
    @FXML
    private TextField fileNameInput;

    private final ImageSelectionPresenter imageSelectionPresenter;

    public MenuSceneController() {
        imageSelectionPresenter = PresenterProvider.provideImageSelectionPresenter();
    }

    @FXML
    private void loadImage(ActionEvent event) {

        String imagePath = imageSelectionPresenter.loadImage();
        if (!imagePath.equals(EMPTY)) {
            ObservableList<String> items = imageComboBox.getItems();
            items.add(imagePath);
            imageComboBox.setItems(items);
        }
    }

    @FXML
    public void saveImage(ActionEvent event) {

        imageSelectionPresenter.setCurrentImagePath(imageComboBox.getSelectionModel().getSelectedItem());
        String fileName = fileNameInput.getText();
        if (fileName.equals(EMPTY)) {
            this.showNoFilenamePopup();
        } else {
            imageSelectionPresenter.saveImage(fileName);
        }
    }

    private void showNoFilenamePopup() {

        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Error");

        Label popupLabel = new Label("Por favor ingrese un nombre para el archivo");
        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> popupWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(popupLabel, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(layout, 250, 100);
        popupWindow.setScene(popupScene);
        popupWindow.showAndWait();
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
