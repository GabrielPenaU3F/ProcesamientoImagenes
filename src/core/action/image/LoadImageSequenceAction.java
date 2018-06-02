package core.action.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import core.repository.ImageRepository;
import core.service.ImageRawService;
import core.service.OpenFileService;
import domain.customimage.CustomImage;
import ij.io.Opener;
import presentation.util.InsertValuePopup;

public class LoadImageSequenceAction {

    private final ImageRepository imageRepository;
    private final OpenFileService openFileService;
    private final Opener opener;
    private final ImageRawService imageRawService;

    private List<CustomImage> images;

    public LoadImageSequenceAction(ImageRepository imageRepository, OpenFileService openFileService, Opener opener,
            ImageRawService imageRawService) {
        this.imageRepository = imageRepository;
        this.openFileService = openFileService;
        this.opener = opener;
        this.imageRawService = imageRawService;
    }

    public List<CustomImage> execute() {

        this.images = new ArrayList<>();

        openFileService.openMultiple().ifPresent(files -> {

            files.forEach(file -> {
                String path = file.toPath().toString();
                String extension = FilenameUtils.getExtension(path);

                if(extension.equalsIgnoreCase("raw")){
                    int width = Integer.parseInt(InsertValuePopup.show("Insert width", "256").get());
                    int height = Integer.parseInt(InsertValuePopup.show("Insert height", "256").get());
                    images.add(new CustomImage(imageRawService.load(file, width, height), extension));
                } else {
                    images.add(new CustomImage(opener.openImage(path).getBufferedImage(), extension));
                }
            });
        });

        return imageRepository.saveImageSequence(images);
    }
}
