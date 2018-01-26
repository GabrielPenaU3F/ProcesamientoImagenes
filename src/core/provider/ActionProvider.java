package core.provider;

import core.action.*;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static LoadImageAction loadImageAction;
    private static SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction;
    private static GetCurrentImagePathAction getCurrentImagePathAction;
    private static SaveImageAction saveImageAction;

    public static GetImageAction provideGetImageAction() {
        if (getImageAction == null) {
            getImageAction = new GetImageAction(RepositoryProvider.provideImageRepository());
            return getImageAction;
        }
        return getImageAction;
    }

    public static LoadImageAction provideLoadImageAction() {
        if (loadImageAction == null) {
            loadImageAction = new LoadImageAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideOpenFileService(),
                    CommonProvider.provideOpener());
            return loadImageAction;
        }
        return loadImageAction;
    }

    public static SetCurrentImagePathOnRepoAction provideSetCurrentImagePathOnRepoAction() {
        if (setCurrentImagePathOnRepoAction == null) {
            setCurrentImagePathOnRepoAction = new SetCurrentImagePathOnRepoAction(RepositoryProvider.provideImageRepository());
            return setCurrentImagePathOnRepoAction;
        }
        return setCurrentImagePathOnRepoAction;
    }

    public static GetCurrentImagePathAction provideGetCurrentImagePathAction() {

        if (getCurrentImagePathAction == null) {
            getCurrentImagePathAction = new GetCurrentImagePathAction(RepositoryProvider.provideImageRepository());
            return getCurrentImagePathAction;
        }
        return getCurrentImagePathAction;
    }


    public static SaveImageAction provideSaveImageAction() {

        if (saveImageAction == null) {
            saveImageAction = new SaveImageAction();
            return saveImageAction;
        }
        return saveImageAction;

    }
}
