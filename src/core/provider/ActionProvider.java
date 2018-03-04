package core.provider;

import core.action.GetImageListAction;
import core.action.PutModifiedImageAction;
import core.action.currentimage.GetCurrentImagePathAction;
import core.action.currentimage.SetCurrentImagePathAction;
import core.action.edit.LoadModifiedImageAction;
import core.action.edit.ModifyPixelAction;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.SaveImageAction;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static LoadImageAction loadImageAction;
    private static SetCurrentImagePathAction setCurrentImagePathAction;
    private static GetCurrentImagePathAction getCurrentImagePathAction;
    private static SaveImageAction saveImageAction;
    private static ModifyPixelAction modifyPixelAction;
    private static GetImageListAction getImageListAction;
    private static LoadModifiedImageAction loadModifiedImageAction;
    private static PutModifiedImageAction putModifiedImageAction;

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
                    CommonProvider.provideOpener(),
                    ServiceProvider.provideImageRawService());
            return loadImageAction;
        }
        return loadImageAction;
    }

    public static SetCurrentImagePathAction provideSetCurrentImagePathOnRepoAction() {
        if (setCurrentImagePathAction == null) {
            setCurrentImagePathAction = new SetCurrentImagePathAction(RepositoryProvider.provideImageRepository());
            return setCurrentImagePathAction;
        }
        return setCurrentImagePathAction;
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

    public static ModifyPixelAction provideModifyPixelAction() {
        if (modifyPixelAction == null) {
            modifyPixelAction = new ModifyPixelAction(
                    RepositoryProvider.provideImageRepository(),
                    ServiceProvider.provideModifyImageService()
            );
            return modifyPixelAction;
        }
        return modifyPixelAction;
    }

    public static GetImageListAction provideGetImageListAction() {
        if (getImageListAction == null) {
            getImageListAction = new GetImageListAction(RepositoryProvider.provideImageRepository());
            return getImageListAction;
        }
        return getImageListAction;
    }

    public static LoadModifiedImageAction provideLoadModifiedImageAction() {
        if (loadModifiedImageAction == null) {
            loadModifiedImageAction = new LoadModifiedImageAction(RepositoryProvider.provideImageRepository());
            return loadModifiedImageAction;
        }
        return loadModifiedImageAction;
    }

    public static PutModifiedImageAction providePutModifiedImageAction() {
        if (putModifiedImageAction == null) {
            putModifiedImageAction = new PutModifiedImageAction(RepositoryProvider.provideImageRepository());
            return putModifiedImageAction;
        }
        return putModifiedImageAction;
    }
}
