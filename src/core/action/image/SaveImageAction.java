package core.action.image;

import core.service.ImageRawService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageAction {

    private final ImageRawService imageRawService;

    public SaveImageAction(ImageRawService imageRawService) {
        this.imageRawService = imageRawService;
    }

    public Image execute(CustomImage image, String filename, String extension) {

        String fullFilename = filename + "." + extension;
        BufferedImage bufferedImage = image.getBufferedImage();

        try {

            if (extension.equalsIgnoreCase("raw")) {
                this.imageRawService.save(bufferedImage, fullFilename);
            } else {
                ImageIO.write(bufferedImage, extension, new File(fullFilename));
            }

            return SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (IOException e) {
            throw new RuntimeException("IOException");
        }
    }
}
