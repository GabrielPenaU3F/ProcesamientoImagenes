package presentation.presenter;

import core.action.image.SaveImageAction;
import core.action.modifiedimage.GetModifiedImageAction;
import presentation.controller.SaveImageController;

public class SaveImagePresenter {

    private final SaveImageController view;
    private final GetModifiedImageAction getModifiedImageAction;
    private final SaveImageAction saveImageAction;

    public SaveImagePresenter(SaveImageController view, GetModifiedImageAction getModifiedImageAction,
                              SaveImageAction saveImageAction) {
        this.getModifiedImageAction = getModifiedImageAction;
        this.saveImageAction = saveImageAction;
        this.view = view;
    }

    public void saveImage() {
        String filename = this.view.outputName.getText();
        String extension = this.view.formatList.getSelectionModel().getSelectedItems().get(0);
        this.getModifiedImageAction.execute()
                .ifPresent(image -> saveImageAction.execute(image, filename, extension));
    }

    public void fillFormatList() {
        //For some weird reason, if I close the Save Image window, and then I load it again, these items on the list dissappear.
        this.view.formatList.getItems().add("RAW");
        this.view.formatList.getItems().add("PPM");
        this.view.formatList.getItems().add("PGM");
        this.view.formatList.getItems().add("PNG");
        this.view.formatList.getItems().add("JPG");
    }

    public void initialize() {
        this.fillFormatList();
    }
}
