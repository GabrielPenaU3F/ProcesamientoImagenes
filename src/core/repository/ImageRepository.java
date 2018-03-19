package core.repository;


import domain.customimage.CustomImage;

import java.util.*;

public class ImageRepository {

    private Map<String, CustomImage> images;
    private String currentImage;
    private CustomImage currentModifiedImage;

    public ImageRepository() {
        this.images = new HashMap<>();
    }

    public void put(String path, CustomImage image) {
        this.images.put(path, image);
    }

    public CustomImage get(String path) {
        return this.images.get(path);
    }

    public void put(String currentImagePath) {
        this.currentImage = currentImagePath;
    }

    public Optional<String> getCurrentImage() {
        return Optional.ofNullable(currentImage);
    }

    public void putCurrentModifiedImage(CustomImage currentModifiedImage) {
        this.currentModifiedImage = currentModifiedImage;
    }

    public Optional<CustomImage> getCurrentModifiedImage() {
        return Optional.ofNullable(currentModifiedImage);
    }

    public List<String> getImages() {
        return new ArrayList<>(images.keySet());
    }
}
