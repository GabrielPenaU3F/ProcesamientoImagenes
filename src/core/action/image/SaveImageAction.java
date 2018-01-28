package core.action.image;

import domain.CustomImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SaveImageAction {

    public void execute(CustomImage image, String filename) {

        String fullFilename = filename + "." + image.getFormatString();
        File outputfile = new File(fullFilename);
        try {
            ImageIO.write(image.getBufferedImage(), image.getFormatString(), outputfile);
        } catch (IOException e) {
            throw new RuntimeException ("IOException");
        }
    }

}
