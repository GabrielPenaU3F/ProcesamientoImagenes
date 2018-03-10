package presentation.controller;

import core.provider.PresenterProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import presentation.presenter.MenuPresenter;
import presentation.scenecreator.ColorGradientSceneCreator;
import presentation.scenecreator.GreyGradientSceneCreator;
import presentation.scenecreator.ImageViewSceneCreator;
import presentation.util.InsertValuePopup;

public class MenuSceneController {

    @FXML
    public ComboBox<String> imageComboBox;

    private final MenuPresenter menuPresenter;
    private static final String EMPTY = "";

    public MenuSceneController() {
        menuPresenter = PresenterProvider.provideImageSelectionPresenter();
    }

    @FXML
    private void loadImage(ActionEvent event) {

        String imageName = menuPresenter.loadImage(InsertValuePopup.show("Insertar nombre", "default"));
        if (!imageName.equals(EMPTY)) {
            ObservableList<String> items = imageComboBox.getItems();
            items.add(imageName);
            imageComboBox.setItems(items);
        }
    }

    @FXML
    public void saveImage(ActionEvent event) {
        String imageSelected = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(imageSelected);
        menuPresenter.saveImage(imageSelected);
    }

    @FXML
    public void showImage(ActionEvent event) {
        menuPresenter.setCurrentImagePath(imageComboBox.getSelectionModel().getSelectedItem());
        new ImageViewSceneCreator().createScene();
    }

    @FXML
    public void reloadImages(ActionEvent actionEvent) {
        imageComboBox.setItems(FXCollections.observableArrayList(menuPresenter.getImages()));
    }

    @FXML
    public void showGreyGradient(ActionEvent event) {
        new GreyGradientSceneCreator().createScene();
    }

    @FXML
    public void showColorGradient(ActionEvent event) {
        new ColorGradientSceneCreator().createScene();
    }

}
