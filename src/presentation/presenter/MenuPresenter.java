package presentation.presenter;

import core.action.GetImageListAction;
import core.action.currentimage.GetCurrentImagePathAction;
import core.action.currentimage.SetCurrentImagePathAction;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.SaveImageAction;
import domain.CustomImage;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MenuPresenter {

    private LoadImageAction loadImageAction;
    private SetCurrentImagePathAction setCurrentImagePathAction;
    private GetCurrentImagePathAction getCurrentImagePathAction;
    private GetImageAction getImageAction;
    private SaveImageAction saveImageAction;
    private GetImageListAction getImageListAction;

    public MenuPresenter(LoadImageAction loadImageAction,
                         SetCurrentImagePathAction setCurrentImagePathAction,
                         GetCurrentImagePathAction getCurrentImagePathAction,
                         GetImageAction getImageAction,
                         SaveImageAction saveImageAction,
                         GetImageListAction getImageListAction) {

        this.loadImageAction = loadImageAction;
        this.setCurrentImagePathAction = setCurrentImagePathAction;
        this.getCurrentImagePathAction = getCurrentImagePathAction;
        this.getImageAction = getImageAction;
        this.saveImageAction = saveImageAction;
        this.getImageListAction = getImageListAction;
    }

    //Loads the image in memory repository and returns its path
    public String loadImage(Supplier<String> nameConsumer) {
        return loadImageAction.execute(nameConsumer.get());
    }

    public void setCurrentImagePath(String currentImagePath) {
        setCurrentImagePathAction.execute(currentImagePath);
    }

    public Optional<String> getCurrentImagePath() {
        return getCurrentImagePathAction.execute();
    }

    public void saveImage(String filename) {
        this.getImage().ifPresent(image -> saveImageAction.execute(image, filename));
    }

    private Optional<CustomImage> getImage() {
        return getImageAction.execute();
    }

    public List<String> getImages() {
        return getImageListAction.execute();
    }
}
