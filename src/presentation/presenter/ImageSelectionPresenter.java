package presentation.presenter;


import core.action.SetCurrentImagePathOnRepoAction;
import core.action.LoadImageAction;

public class ImageSelectionPresenter {

    private LoadImageAction loadImageAction;
    private SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction;

    public ImageSelectionPresenter(LoadImageAction loadImageAction, SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction) {
        this.loadImageAction = loadImageAction;
        this.setCurrentImagePathOnRepoAction = setCurrentImagePathOnRepoAction;
    }

    //Loads the imagen and returns its path
    public String loadImage() {
        return loadImageAction.execute();
    }

    public void setCurrentImagePathOnRepo(String currentImagePath) {
        setCurrentImagePathOnRepoAction.execute(currentImagePath);
    }
}
