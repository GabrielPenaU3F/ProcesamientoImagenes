package core.action;

import core.repository.ImageRepository;
import core.service.OpenFileService;
import domain.CustomImage;
import domain.Format;
import ij.ImagePlus;
import ij.io.Opener;
import org.apache.commons.io.FilenameUtils;

import java.awt.image.BufferedImage;
import java.io.File;

public class LoadImageAction {

    private ImageRepository imageRepository;
    private OpenFileService openFileService;
    private Opener opener;

    public LoadImageAction(ImageRepository imageRepository, OpenFileService openFileService, Opener opener) {
        this.imageRepository = imageRepository;
        this.openFileService = openFileService;
        this.opener = opener;
    }

    public String execute() {
        File file = openFileService.open();
        String path = file.toPath().toString();
        ImagePlus img = opener.openImage(path);
        BufferedImage bufferedImage = img.getBufferedImage();
        String formatString = FilenameUtils.getExtension(path); //Como obtengo el formato real??
        imageRepository.put(path, new CustomImage(bufferedImage, formatString));
        return path;
    }
}
