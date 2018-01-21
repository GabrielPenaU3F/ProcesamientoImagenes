package presentation.presenter;


import core.action.SaveCurrentImagePathAction;
import core.action.SaveImageAction;

public class ImageSelectionPresenter {

    private SaveImageAction saveImageAction;
    private SaveCurrentImagePathAction saveCurrentImagePathAction;

    public ImageSelectionPresenter(SaveImageAction saveImageAction, SaveCurrentImagePathAction saveCurrentImagePathAction) {
        this.saveImageAction = saveImageAction;
        this.saveCurrentImagePathAction = saveCurrentImagePathAction;
    }

    public String saveImage() {
        return saveImageAction.execute();
    }

    public void saveCurrentImagePath(String currentImagePath) {
        saveCurrentImagePathAction.execute(currentImagePath);
    }
}
