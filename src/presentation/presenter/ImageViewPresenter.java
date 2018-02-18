package presentation.presenter;

import core.action.edit.LoadModifiedImageAction;
import core.action.edit.ModifyPixelAction;
import core.action.image.GetImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import presentation.util.InsertValuePopup;

import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Supplier;

public class ImageViewPresenter {

    private GetImageAction getImageAction;
    private ModifyPixelAction modifyPixelAction;
    private LoadModifiedImageAction loadModifiedImageAction;

    public ImageViewPresenter(GetImageAction getImageAction,
                              ModifyPixelAction modifyPixelAction,
                              LoadModifiedImageAction loadModifiedImageAction) {
        this.getImageAction = getImageAction;
        this.modifyPixelAction = modifyPixelAction;
        this.loadModifiedImageAction = loadModifiedImageAction;
    }

    private Optional<CustomImage> getCurrentImage() {
        return this.getImageAction.execute();
    }

    public Optional<Image> getFXImage() {
        return this.getCurrentImage().map(customImage -> {
            BufferedImage bufferedImage = customImage.getBufferedImage();
            return SwingFXUtils.toFXImage(bufferedImage, null);
        });
    }

    public Optional<Integer> getRGB(Integer pixelX, Integer pixelY) {
        return this.getCurrentImage().map(customImage -> customImage.getPixelValue(pixelX, pixelY));
    }

    public Image modifyPixelValue(Integer pixelX, Integer pixelY, String value) {
        return modifyPixelAction.execute(pixelX, pixelY, value);
    }

    public Optional<Image> saveChanges() {
        Supplier<String> fileNameSupplier = InsertValuePopup.show("Save Modified Image", "modified");
        return loadModifiedImageAction.execute(fileNameSupplier);
    }
}
