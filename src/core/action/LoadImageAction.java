package core.action;

import core.repository.ImageRepository;
import core.service.OpenFileService;
import ij.ImagePlus;
import ij.io.Opener;
import javafx.embed.swing.SwingFXUtils;
import domain.Image;

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
        //We should create an Image class (OUR IMAGE CLASS) based on this BufferedImage object
        imageRepository.put(path, bufferedImage);
        return path;
    }
}
