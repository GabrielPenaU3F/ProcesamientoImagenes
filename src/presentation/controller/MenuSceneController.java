package presentation.controller;

import core.provider.PresenterProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presentation.presenter.ImageSelectionPresenter;
import presentation.scenecreator.GreyGradientSceneCreator;
import presentation.scenecreator.ImageViewSceneCreator;
import presentation.util.InsertValuePopup;

import java.util.List;
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

        String imageName = imageSelectionPresenter.loadImage(InsertValuePopup.show("Insertar nombre", "default"));
        if (!imageName.equals(EMPTY)) {
            ObservableList<String> items = imageComboBox.getItems();
            items.add(imageName);
            imageComboBox.setItems(items);
        }
    }

    @FXML
    public void saveImage(ActionEvent event) {

        String imageSelected = imageComboBox.getSelectionModel().getSelectedItem();
        imageSelectionPresenter.setCurrentImagePath(imageSelected);
        imageSelectionPresenter.saveImage(imageSelected);
    }

    @FXML
    public void showImage(ActionEvent event) {
        imageSelectionPresenter.setCurrentImagePath(imageComboBox.getSelectionModel().getSelectedItem());
        new ImageViewSceneCreator().createScene();
    }

    @FXML
    public void reloadImages(ActionEvent actionEvent) {
        imageComboBox.setItems(FXCollections.observableArrayList(imageSelectionPresenter.getImages()));
    }

    @FXML
    public void showGreyGradient(ActionEvent event) {

        new GreyGradientSceneCreator().createScene();

    }

}
