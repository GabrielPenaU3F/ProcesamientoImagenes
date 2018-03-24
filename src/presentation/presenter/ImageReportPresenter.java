package presentation.presenter;

import core.action.image.CreateImageInformAction;
import core.action.modifiedimage.GetModifiedImageAction;
import domain.ImageInform;
import javafx.embed.swing.SwingFXUtils;

import java.util.Optional;

public class ImageReportPresenter {

    private GetModifiedImageAction getModifiedImageAction;
    private CreateImageInformAction createImageInformAction;

    public ImageReportPresenter(GetModifiedImageAction getModifiedImageAction, CreateImageInformAction createImageInformAction) {
        this.getModifiedImageAction = getModifiedImageAction;
        this.createImageInformAction = createImageInformAction;
    }

    public Optional<ImageInform> createImageInform() {
        return getModifiedImageAction.execute()
                .map(customImage -> SwingFXUtils.toFXImage(customImage.getBufferedImage(), null))
                .map(image -> createImageInformAction.execute(image));
    }
}
