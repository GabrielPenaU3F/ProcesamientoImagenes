package core.action.image;

import core.repository.ImageRepository;
import core.service.ImageRawService;
import core.service.OpenFileService;
import domain.customimage.CustomImage;
import ij.io.Opener;
import org.apache.commons.io.FilenameUtils;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class LoadImageAction {

    private ImageRepository imageRepository;
    private OpenFileService openFileService;
    private Opener opener;
    private String path = "";
    private ImageRawService imageRawService;
    private CustomImage image;

    public LoadImageAction(ImageRepository imageRepository, OpenFileService openFileService, Opener opener,
                           ImageRawService imageRawService) {
        this.imageRepository = imageRepository;
        this.openFileService = openFileService;
        this.opener = opener;
        this.imageRawService = imageRawService;
    }

    public CustomImage execute() {

        image = new CustomImage(new BufferedImage(1,1, TYPE_INT_ARGB), "png");

        openFileService.open().ifPresent(file -> {
            path = file.toPath().toString();
            String extension = FilenameUtils.getExtension(path);
            if(extension.equalsIgnoreCase("raw")){
                image = putOnRepository(extension, imageRawService.load(file, 256, 256));
            } else {
                image = putOnRepository(extension, opener.openImage(path).getBufferedImage());
            }
        });

        return image;
    }

    private CustomImage putOnRepository(String extension, BufferedImage bufferedImage) {
        return imageRepository.saveImage(new CustomImage(bufferedImage, extension));
    }
}
