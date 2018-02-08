package core.action.image;

import domain.CustomImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveImageAction {

    public void execute(CustomImage image, String filename) {

        String fullFilename = filename + "." + image.getFormatString();
        try {
            ImageIO.write(image.getBufferedImage(), image.getFormatString(), new File(fullFilename));
        } catch (IOException e) {
            throw new RuntimeException ("IOException");
        }
    }

}
