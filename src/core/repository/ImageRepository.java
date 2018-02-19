package core.repository;


import domain.CustomImage;

import java.util.*;

public class ImageRepository {

    private Map<String, CustomImage> images;
    private String currentImagePath;
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
        this.currentImagePath = currentImagePath;
    }

    public Optional<String> getCurrentImagePath() {
        return Optional.ofNullable(currentImagePath);
    }

    public void putCurrentModifiedImage(CustomImage currentModifiedImage) {
        this.currentModifiedImage = currentModifiedImage;
    }

    public Optional<CustomImage> getCurrentModifiedImage() {
        return Optional.ofNullable(currentModifiedImage);
    }

    public int size(){
        return images.size();
    }

    public List<String> getImages() {
        return new ArrayList<>(images.keySet());
    }
}
