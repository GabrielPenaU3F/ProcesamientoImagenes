package core.action.image;

import core.repository.ImageRepository;
import core.service.ImageRawService;
import core.service.OpenFileService;
import domain.CustomImage;
import ij.io.Opener;
import org.apache.commons.io.FilenameUtils;

import java.awt.image.BufferedImage;

public class LoadImageAction {

    private ImageRepository imageRepository;
    private OpenFileService openFileService;
    private Opener opener;
    private String path = "";
    private ImageRawService imageRawService;

    public LoadImageAction(ImageRepository imageRepository, OpenFileService openFileService, Opener opener,
                           ImageRawService imageRawService) {
        this.imageRepository = imageRepository;
        this.openFileService = openFileService;
        this.opener = opener;
        this.imageRawService = imageRawService;
    }

    public String execute(String fileName) {

        openFileService.open().ifPresent(file -> {

            path = file.toPath().toString();
            String extension = FilenameUtils.getExtension(path);
            if(extension.equalsIgnoreCase("raw")){
                putOnRepository(fileName, extension, imageRawService.load(file, 256, 256));
            } else {
                putOnRepository(fileName, extension, opener.openImage(path).getBufferedImage());
            }

        });

        return fileName;
    }

    private void putOnRepository(String filePath, String extension, BufferedImage bufferedImage) {
        imageRepository.put(filePath, new CustomImage(bufferedImage, extension));
    }
}
