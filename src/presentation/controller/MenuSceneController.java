package presentation.controller;

import aux_classes.ChannelSemaphore;
import core.action.figure.semaphore.FigureSemaphore;
import core.action.gradient.semaphore.GradientSemaphore;
import core.provider.PresenterProvider;
import domain.Channel;
import domain.Figure;
import domain.Gradient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import presentation.presenter.MenuPresenter;
import presentation.scenecreator.ChannelSceneCreator;
import presentation.scenecreator.ImageGradientSceneCreator;
import presentation.scenecreator.ImageViewSceneCreator;
import presentation.scenecreator.ImageFigureSceneCreator;
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
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        menuPresenter.saveImage(selectedImage);
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
        GradientSemaphore.setValue(Gradient.GREY);
        new ImageGradientSceneCreator().createScene();
    }

    @FXML
    public void showColorGradient(ActionEvent event) {
        GradientSemaphore.setValue(Gradient.COLOR);
        new ImageGradientSceneCreator().createScene();
    }

    @FXML
    public void showRGBImageRedChannel(ActionEvent event) {
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.RED);
        new ChannelSceneCreator().createScene();
    }

    @FXML
    public void showRGBImageGreenChannel(ActionEvent event) {
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.GREEN);
        new ChannelSceneCreator().createScene();
    }

    @FXML
    public void showRGBImageBlueChannel(ActionEvent event) {
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.BLUE);
        new ChannelSceneCreator().createScene();
    }

    @FXML
    public void showImageWithQuadrate(ActionEvent actionEvent) {
        FigureSemaphore.setValue(Figure.QUADRATE);
        new ImageFigureSceneCreator().createScene();
    }

    @FXML
    public void showImageWithCircle(ActionEvent actionEvent) {
        FigureSemaphore.setValue(Figure.CIRCLE);
        new ImageFigureSceneCreator().createScene();
    }

    @FXML
    public void showHueHSVChannel(ActionEvent event) {

        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.HUE);
        new ChannelSceneCreator().createScene();

    }

    @FXML
    public void showSaturationHSVChannel(ActionEvent event) {

        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.SATURATION);
        new ChannelSceneCreator().createScene();

    }

    @FXML
    public void showValueHSVChannel(ActionEvent event) {

        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        ChannelSemaphore.setValue(Channel.VALUE);
        new ChannelSceneCreator().createScene();

    }
}
