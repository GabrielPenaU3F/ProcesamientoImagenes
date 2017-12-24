package core.provider;

import core.action.GetImageAction;
import core.action.SaveImageAction;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static SaveImageAction saveImageAction;

    public static GetImageAction provideGetImageAction() {
        if (getImageAction == null) {
            getImageAction = new GetImageAction(RepositoryProvider.provideImageRepository());
            return getImageAction;
        }
        return getImageAction;
    }

    public static SaveImageAction provideSaveImageAction() {
        if (saveImageAction == null) {
            saveImageAction = new SaveImageAction(RepositoryProvider.provideImageRepository());
            return saveImageAction;
        }
        return saveImageAction;
    }
}
