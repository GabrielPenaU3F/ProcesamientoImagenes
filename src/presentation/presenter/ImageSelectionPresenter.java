package presentation.presenter;


import core.action.*;

import java.awt.image.BufferedImage;

public class ImageSelectionPresenter {

    private LoadImageAction loadImageAction;
    private SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction;
    private GetCurrentImagePathAction getCurrentImagePathAction;
    private GetImageAction getImageAction;
    private SaveImageAction saveImageAction;


    public ImageSelectionPresenter(LoadImageAction loadImageAction, SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction, GetCurrentImagePathAction getCurrentImagePathAction, GetImageAction getImageAction, SaveImageAction saveImageAction) {
        this.loadImageAction = loadImageAction;
        this.setCurrentImagePathOnRepoAction = setCurrentImagePathOnRepoAction;
        this.getCurrentImagePathAction = getCurrentImagePathAction;
        this.getImageAction = getImageAction;
        this.saveImageAction = saveImageAction;
    }

    //Loads the imagen and returns its path
    public String loadImage() {
        return loadImageAction.execute();
    }

    public void setCurrentImagePathOnRepo(String currentImagePath) {
        setCurrentImagePathOnRepoAction.execute(currentImagePath);
    }

    public String getCurrentImagePath() {
        return getCurrentImagePathAction.execute();
    }

    public void saveImage(String filename) {

        BufferedImage image = this.getImage();
        saveImageAction.execute(image, filename);

    }

    private BufferedImage getImage() {
        return getImageAction.execute();
    }
}
