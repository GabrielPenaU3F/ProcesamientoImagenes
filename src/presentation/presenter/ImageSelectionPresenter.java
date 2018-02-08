package presentation.presenter;

import core.action.currentimage.GetCurrentImagePathAction;
import core.action.currentimage.SetCurrentImagePathAction;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.SaveImageAction;
import domain.CustomImage;
import io.reactivex.functions.Consumer;

import java.util.Optional;
import java.util.function.Supplier;

public class ImageSelectionPresenter {

    private LoadImageAction loadImageAction;
    private SetCurrentImagePathAction setCurrentImagePathAction;
    private GetCurrentImagePathAction getCurrentImagePathAction;
    private GetImageAction getImageAction;
    private SaveImageAction saveImageAction;


    public ImageSelectionPresenter(LoadImageAction loadImageAction,
                                   SetCurrentImagePathAction setCurrentImagePathAction,
                                   GetCurrentImagePathAction getCurrentImagePathAction,
                                   GetImageAction getImageAction,
                                   SaveImageAction saveImageAction) {
        this.loadImageAction = loadImageAction;
        this.setCurrentImagePathAction = setCurrentImagePathAction;
        this.getCurrentImagePathAction = getCurrentImagePathAction;
        this.getImageAction = getImageAction;
        this.saveImageAction = saveImageAction;
    }

    //Loads the image in memory repository and returns its path
    public String loadImage(Supplier<String> nameConsumer) {
        return loadImageAction.execute(nameConsumer);
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
}
