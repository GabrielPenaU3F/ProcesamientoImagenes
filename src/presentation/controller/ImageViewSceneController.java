package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import presentation.presenter.ImageViewPresenter;

import java.awt.image.BufferedImage;

public class ImageViewSceneController {


    @FXML public ImageView imageView;
    private ImageViewPresenter imageViewPresenter;
    @FXML public Pane imageViewPane;
    @FXML public TextField pixelX;
    @FXML public TextField pixelY;
    @FXML public TextField pixelValue;
    @FXML public TextField pixelRValue;
    @FXML public TextField pixelGValue;
    @FXML public TextField pixelBValue;




    public ImageViewSceneController() {
        this.imageViewPresenter = PresenterProvider.provideImageViewPresenter();
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        Image image = this.imageViewPresenter.getFXImage();
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(e -> {
            Integer mouseX = (int)e.getX();
            Integer mouseY = (int)e.getY();
            //Estos metodos getX y getY devuelven double. Pero la posicion en pixeles debe ser entera. ¿Estará bien castearlo a int?
            pixelX.setText(mouseX.toString());
            pixelY.setText(mouseY.toString());

            Integer rgb = imageViewPresenter.getRGB(mouseX, mouseY);
            pixelValue.setText(rgb.toString());

        });
        imageView.setImage(image);
    }

}
