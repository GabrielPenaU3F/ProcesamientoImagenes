package core.repository;


import domain.CustomImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ImageRepository {

    private Map<String, CustomImage> images;
    private String currentImagePath;

    public ImageRepository() {
        this.images = new HashMap<>();
        this.currentImagePath = "";
    }

    public void put(String path, CustomImage image) {
        this.images.put(path, image);
    }

    public CustomImage get(String path) {
        return this.images.get(path);
    }

    public void put(String currentImagePath) {
        this.currentImagePath = currentImagePath;
    }

    public Optional<String> getCurrentImagePath() {
        return Optional.ofNullable(currentImagePath);
    }
}
