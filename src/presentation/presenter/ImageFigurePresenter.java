package presentation.presenter;

import core.action.figure.CreateImageWithFigureAction;
import core.action.image.SaveImageAction;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ImageFigurePresenter {

    private SaveImageAction saveImageAction;
    private CreateImageWithFigureAction createImageWithFigureAction;

    public ImageFigurePresenter(SaveImageAction saveImageAction,
                                CreateImageWithFigureAction createImageWithFigureAction) {

        this.saveImageAction = saveImageAction;
        this.createImageWithFigureAction = createImageWithFigureAction;
    }

    public Image createImageWithFigure(int width, int height) {
        return createImageWithFigureAction.execute(width, height);
    }

    public void saveImage(Image image, String fileName) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        CustomImage customImage = new CustomImage(bufferedImage, "png");
        saveImageAction.execute(customImage, fileName);
    }
}
