package core.provider;

import core.action.GetImageAction;
import core.action.SetCurrentImagePathOnRepoAction;
import core.action.LoadImageAction;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static LoadImageAction loadImageAction;
    private static SetCurrentImagePathOnRepoAction setCurrentImagePathOnRepoAction;

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
}
