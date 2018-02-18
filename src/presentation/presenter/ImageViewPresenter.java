package presentation.presenter;

import core.action.CheckIfModifyingAction;
import core.action.ModifyPixelAction;
import core.action.image.GetImageAction;
import core.action.image.GetModifiedImageAction;
import core.action.image.SaveChangesAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageViewPresenter {

    private GetImageAction getImageAction;
    private ModifyPixelAction modifyPixelAction;
    private CheckIfModifyingAction checkIfModifyingAction;
    private GetModifiedImageAction getModifiedImageAction;
    private SaveChangesAction saveChangesAction;

    public ImageViewPresenter(GetImageAction getImageAction, ModifyPixelAction modifyPixelAction1, CheckIfModifyingAction checkIfModifyingAction, GetModifiedImageAction getModifiedImageAction, SaveChangesAction saveChangesAction) {
        this.getImageAction = getImageAction;
        this.modifyPixelAction = modifyPixelAction1;
        this.checkIfModifyingAction = checkIfModifyingAction;
        this.getModifiedImageAction = getModifiedImageAction;
        this.saveChangesAction = saveChangesAction;
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

    public void modifyPixelValue(Integer pixelX, Integer pixelY, Double value) {
        CustomImage image;
        /*If I'm modyfing, it'll keep modifying that image
        If I'm not (already stop modifying), it'll modify the shown image
         */
        if(this.checkIfModifyingAction.execute()) {
            image = this.getModifiedImageAction.execute();
        } else {
            Optional<CustomImage> imageOptional = this.getImageAction.execute();
            image = imageOptional.get();
        }
        modifyPixelAction.execute(image, pixelX, pixelY, value);
        //SwingFXUtils.toFXImage(this.getModifiedImageAction.execute().getBufferedImage(), null);
    }

    public void saveChanges() {
        this.saveChangesAction.execute();
    }

    public Image getFXModifiedFXImage() {
        return SwingFXUtils.toFXImage(this.getModifiedImageAction.execute().getBufferedImage(), null);
    }

    public Integer getModifiedRGB(int pixelX, int pixelY) {
        return this.getModifiedImage().getPixelValue(pixelX, pixelY);
    }

    public CustomImage getModifiedImage() {
        if (this.checkIfModifyingAction.execute()) return getModifiedImageAction.execute();
        else throw new RuntimeException("Not modifying");

    }

    public boolean checkIfModifying() {

        return this.checkIfModifyingAction.execute();
    }
}
