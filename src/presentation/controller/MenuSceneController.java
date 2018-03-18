package presentation.controller;

import core.action.channels.semaphore.RGBSemaphore;
import core.action.figure.semaphore.FigureSemaphore;
import core.action.gradient.semaphore.GradientSemaphore;
import core.provider.PresenterProvider;
import domain.Figure;
import domain.Gradient;
import domain.RGBChannel;
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
        RGBSemaphore.setValue(RGBChannel.RED);
        new ChannelSceneCreator().createScene();
    }

    @FXML
    public void showRGBImageGreenChannel(ActionEvent event) {
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        RGBSemaphore.setValue(RGBChannel.GREEN);
        new ChannelSceneCreator().createScene();
    }

    @FXML
    public void showRGBImageBlueChannel(ActionEvent event) {
        String selectedImage = imageComboBox.getSelectionModel().getSelectedItem();
        menuPresenter.setCurrentImagePath(selectedImage);
        RGBSemaphore.setValue(RGBChannel.BLUE);
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
}
