package core.repository;

import domain.customimage.CustomImage;

import java.util.Optional;

public class ImageRepository {

    private CustomImage image;
    private CustomImage modifiedImage;

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
}
