package presentation.controller;

import core.provider.PresenterProvider;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
        if(fileName == "") {
            fileName = "Image1"; //Default name
        }
        imageSelectionPresenter.saveImage(fileName);

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
