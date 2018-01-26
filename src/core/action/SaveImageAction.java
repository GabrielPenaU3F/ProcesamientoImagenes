package core.action;

import domain.CustomImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SaveImageAction {

    //TODO: The format is hardcoded. We should retain the original format of the image

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
