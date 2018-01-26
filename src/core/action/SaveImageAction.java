package core.action;

import domain.Format;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImageAction {

    //TODO: The format is hardcoded. We should retain the original format of the image

    public void execute(BufferedImage image, String filename) {

        File outputfile = new File(filename += ".jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException ("IOException");
        }
    }

}
