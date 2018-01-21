package core.provider;

import core.action.GetImageAction;
import core.action.SaveCurrentImagePathAction;
import core.action.SaveImageAction;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static SaveImageAction saveImageAction;
    private static SaveCurrentImagePathAction saveCurrentImagePathAction;

    public static GetImageAction provideGetImageAction() {
        if (getImageAction == null) {
            getImageAction = new GetImageAction(RepositoryProvider.provideImageRepository());
            return getImageAction;
        }
        return getImageAction;
    }

    public static SaveImageAction provideSaveImageAction() {
        if (saveImageAction == null) {
            saveImageAction = new SaveImageAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideOpenFileService(),
                    CommonProvider.provideOpener());
            return saveImageAction;
        }
        return saveImageAction;
    }

    public static SaveCurrentImagePathAction provideSaveCurrentImagePathAction() {
        if (saveCurrentImagePathAction == null) {
            saveCurrentImagePathAction = new SaveCurrentImagePathAction(RepositoryProvider.provideImageRepository());
            return saveCurrentImagePathAction;
        }
        return saveCurrentImagePathAction;
    }
}
