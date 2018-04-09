package core.provider;

import presentation.controller.MainSceneController;

public class ViewProvider {

    private static MainSceneController mainSceneController;

    public static void setMainView(MainSceneController mainView) {
        mainSceneController = mainView;
    }

    public static MainSceneController provideMainView() {
        return mainSceneController;
    }
}
