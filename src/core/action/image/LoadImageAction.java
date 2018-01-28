package core.action.image;

import core.repository.ImageRepository;
import core.service.OpenFileService;
import domain.CustomImage;
import ij.ImagePlus;
import ij.io.Opener;
import org.apache.commons.io.FilenameUtils;

import java.awt.image.BufferedImage;

public class LoadImageAction {

    private ImageRepository imageRepository;
    private OpenFileService openFileService;
    private Opener opener;
    private String path = "";

    public LoadImageAction(ImageRepository imageRepository, OpenFileService openFileService, Opener opener) {
        this.imageRepository = imageRepository;
        this.openFileService = openFileService;
        this.opener = opener;
    }

    public String execute() {

        openFileService.open().ifPresent(file -> {

            path = file.toPath().toString();
            ImagePlus img = opener.openImage(path);
            BufferedImage bufferedImage = img.getBufferedImage();

            imageRepository.put(path, new CustomImage(bufferedImage, FilenameUtils.getExtension(path)));
        });

        return path;
    }
}
