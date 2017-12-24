package presentation.presenter;


import core.action.GetImageAction;
import core.action.SaveImageAction;

public class ImageSelectionPresenter {

    private GetImageAction getImageAction;
    private SaveImageAction saveImageAction;

    public ImageSelectionPresenter(GetImageAction getImageAction, SaveImageAction saveImageAction) {
        this.getImageAction = getImageAction;
        this.saveImageAction = saveImageAction;
    }

    public String getImage(String key) {
        return getImageAction.execute(key);
    }

    public void saveImage(String key, String value) {
        saveImageAction.execute(key, value);
    }
}
