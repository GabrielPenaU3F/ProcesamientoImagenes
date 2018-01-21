package core.repository;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageRepository {

    private Map<String, Image> images;
    private String currentImagePath;

    public ImageRepository() {
        this.images = new HashMap<>();
        this.currentImagePath = "";
    }

    public void put(String path, Image image) {
        this.images.put(path, image);
    }

    public Image get(String path) {
        return this.images.get(path);
    }

    public void put(String currentImagePath) {
        this.currentImagePath = currentImagePath;
    }

    public String getCurrentImagePath() {
        return currentImagePath;
    }
}
