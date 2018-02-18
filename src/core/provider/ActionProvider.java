package core.provider;

import core.action.CheckIfModifyingAction;
import core.action.GetImageListAction;
import core.action.ModifyPixelAction;
import core.action.currentimage.GetCurrentImagePathAction;
import core.action.currentimage.SetCurrentImagePathAction;
import core.action.image.*;

public class ActionProvider {

    private static GetImageAction getImageAction;
    private static LoadImageAction loadImageAction;
    private static SetCurrentImagePathAction setCurrentImagePathAction;
    private static GetCurrentImagePathAction getCurrentImagePathAction;
    private static SaveImageAction saveImageAction;
    private static ModifyPixelAction modifyPixelAction;
    private static GetImageListAction getImageListAction;
    private static CheckIfModifyingAction checkIfModifyingAction;
    private static GetModifiedImageAction getModifiedImageAction;
    private static SaveChangesAction saveChangesAction;

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
            modifyPixelAction = new ModifyPixelAction(RepositoryProvider.provideImageRepository());
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

    public static CheckIfModifyingAction provideCheckIfModifyingAction() {
        if (checkIfModifyingAction == null) {
            checkIfModifyingAction = new CheckIfModifyingAction(RepositoryProvider.provideImageRepository());
            return checkIfModifyingAction;
        }
        return checkIfModifyingAction;
    }

    public static GetModifiedImageAction provideGetModifiedImageAction() {
        if (getModifiedImageAction == null) {
            getModifiedImageAction = new GetModifiedImageAction(RepositoryProvider.provideImageRepository());
            return getModifiedImageAction;
        }
        return getModifiedImageAction; 
    }

    public static SaveChangesAction provideSaveChangesAction() {
        if (saveChangesAction == null) {
            saveChangesAction = new SaveChangesAction(RepositoryProvider.provideImageRepository());
            return saveChangesAction;
        }
        return saveChangesAction;
    }
}
