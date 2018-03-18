package presentation.presenter;

import core.action.image.CreateImageInformAction;
import core.action.modifiedimage.GetModifiedImageAction;
import domain.ImageInform;

import java.util.Optional;

public class ImageInformPresenter {

    private GetModifiedImageAction getModifiedImageAction;
    private CreateImageInformAction createImageInformAction;

    public ImageInformPresenter(GetModifiedImageAction getModifiedImageAction, CreateImageInformAction createImageInformAction) {
        this.getModifiedImageAction = getModifiedImageAction;
        this.createImageInformAction = createImageInformAction;
    }

    public Optional<ImageInform> createImageInform() {
        return getModifiedImageAction.execute()
                .map(image -> createImageInformAction.execute(image));
    }
}
