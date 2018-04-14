package presentation.presenter;

import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.filter.MediaMask;
import presentation.controller.MediaFilterSceneController;

public class MediaFilterPresenter {

    private final MediaFilterSceneController view;
    private final GetImageAction getImageAction;
    private final ApplyFilterAction applyFilterAction;

    public MediaFilterPresenter(MediaFilterSceneController view,
                                GetImageAction getImageAction,
                                ApplyFilterAction applyFilterAction) {
        this.view = view;
        this.getImageAction = getImageAction;
        this.applyFilterAction = applyFilterAction;
    }

    public void onApply() {

        int size = Integer.parseInt(view.sizeTextField.getText());

        if (sizeMaskMustBeAnOddInteger(size)) return;

        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    applyFilterAction.execute(customImage, new MediaMask(size));
                    view.closeWindow();
                });
    }

    private boolean sizeMaskMustBeAnOddInteger(int size) {
        if(size % 2 == 0) {
            view.closeWindow();
            return true;
        }
        return false;
    }
}
