package presentation.presenter;


import core.action.GetImageAction;
import core.action.SetCurrentImagePathOnRepoAction;
import core.action.GetCurrentImagePathAction;
import core.action.LoadImageAction;
import java.awt.image.BufferedImage;

public class ImageSelectionPresenter {

    private LoadImageAction loadImageAction;
    private SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction;
    private GetCurrentImagePathAction getCurrentImagePathAction;
    private GetImageAction getImageAction;


    public ImageSelectionPresenter(LoadImageAction loadImageAction, SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction, GetCurrentImagePathAction getCurrentImagePathAction, GetImageAction getImageAction) {
        this.loadImageAction = loadImageAction;
        this.setCurrentImagePathOnRepoAction = setCurrentImagePathOnRepoAction;
        this.getCurrentImagePathAction = getCurrentImagePathAction;
        this.getImageAction = getImageAction;
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

    public void saveImage() {

        BufferedImage image = this.getImage();


    }

    private BufferedImage getImage() {
        return getImageAction.execute();
    }
}
