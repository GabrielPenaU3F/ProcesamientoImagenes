package core.action.image;

import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageAction {

    public Image execute(CustomImage image, String filename) {

        String fullFilename = filename + "." + image.getFormatString();
        try {
            BufferedImage bufferedImage = image.getBufferedImage();
            ImageIO.write(bufferedImage, image.getFormatString(), new File(fullFilename));
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            throw new RuntimeException("IOException");
        }
    }

}
