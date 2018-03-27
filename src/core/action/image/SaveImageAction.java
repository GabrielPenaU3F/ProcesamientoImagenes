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

    private ImageRawService imageRawService;

    public SaveImageAction(ImageRawService imageRawService) {
        this.imageRawService = imageRawService;
    }

    public Image execute(CustomImage image, String filename) {

        String ext = image.getFormatString();
        String fullFilename = filename + "." + ext;
        BufferedImage bufferedImage = image.getBufferedImage();

        try {

            if(ext.equalsIgnoreCase("raw")) {
                imageRawService.save(bufferedImage, fullFilename);
            } else {
                ImageIO.write(bufferedImage, ext, new File(fullFilename));
            }

            return SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (IOException e) {
            throw new RuntimeException("IOException");
        }
    }
}
