package presentation.presenter;

import core.action.image.CreateImageInformAction;
import core.action.image.GetModifiedImageAction;
import domain.ImageInform;
import javafx.embed.swing.SwingFXUtils;

import java.util.Optional;

public class ImageReportPresenter {

    private final GetModifiedImageAction getModifiedImageAction;
    private final CreateImageInformAction createImageInformAction;

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
