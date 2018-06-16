package core.repository;

import domain.customimage.CustomImage;

import java.util.List;
import java.util.Optional;

public class ImageRepository {

    private CustomImage image;
    private CustomImage modifiedImage;
    private CustomImage originalImageBackup;
    private List<CustomImage> imageSequence;

    public CustomImage saveImage(CustomImage image) {
        this.image = image;
        return this.image;
    }

    public Optional<CustomImage> getImage() {
        return Optional.ofNullable(this.image);
    }

    public CustomImage saveModifiedImage(CustomImage image) {
        this.modifiedImage = image;
        return modifiedImage;
    }

    public Optional<CustomImage> getModifiedImage() {
        return Optional.ofNullable(this.modifiedImage);
    }

    public void setOriginalImageBackup(CustomImage originalImageBackup) {
        this.originalImageBackup = originalImageBackup;
    }

    public CustomImage getOriginalImageBackup(){
        return this.originalImageBackup;
    }

    public List<CustomImage> saveImageSequence(List<CustomImage> imageSequence) {
        if(!imageSequence.isEmpty()) this.image = imageSequence.get(0);
        this.imageSequence = imageSequence;
        return imageSequence;
    }

    public Optional<List<CustomImage>> getImageSequence() {
        return Optional.ofNullable(imageSequence);
    }
}
