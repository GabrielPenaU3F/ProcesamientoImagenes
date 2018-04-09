package core.provider;

import core.repository.ImageRepository;

class RepositoryProvider {

    private static ImageRepository imageRepository;

    public static ImageRepository provideImageRepository() {
        if (imageRepository == null) {
            imageRepository = new ImageRepository();
        }
        return imageRepository;
    }

}
