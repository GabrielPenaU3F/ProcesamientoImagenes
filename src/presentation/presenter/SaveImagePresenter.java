package presentation.presenter;

import core.action.image.GetImageAction;
import core.action.image.SaveImageAction;
import core.action.image.GetModifiedImageAction;
import domain.customimage.Format;
import presentation.controller.SaveImageController;

public class SaveImagePresenter {

    private final SaveImageController view;
    private final GetImageAction getImageAction;
    private final SaveImageAction saveImageAction;

    public SaveImagePresenter(SaveImageController view, GetImageAction getImageAction,
                              SaveImageAction saveImageAction) {
        this.getImageAction = getImageAction;
        this.saveImageAction = saveImageAction;
        this.view = view;
    }

    public void saveImage() {
        String filename = this.view.outputName.getText();
        String extension = this.view.formatList.getSelectionModel().getSelectedItems().get(0);
        this.getImageAction.execute()
                .ifPresent(image -> {
                    if(saveImageAction.execute(image, filename, extension) != null) {
                        this.view.closeWindow();
                    }
                });
    }

    private void fillFormatList() {
        //For some weird reason, if I close the Save Image window, and then I load it again, these items on the list dissappear.
        this.view.formatList.getItems().add(Format.RAW);
        this.view.formatList.getItems().add(Format.PPM);
        this.view.formatList.getItems().add(Format.PGM);
        this.view.formatList.getItems().add(Format.PNG);
        this.view.formatList.getItems().add(Format.JPG);
    }

    public void initialize() {
        this.fillFormatList();
    }
}
