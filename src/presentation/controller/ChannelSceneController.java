package presentation.controller;

import aux_classes.ChannelSemaphore;
import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import presentation.presenter.ChannelScenePresenter;

public class ChannelSceneController {

    private final ChannelScenePresenter channelScenePresenter;

    @FXML
    ImageView imageView;

    public ChannelSceneController() {
        this.channelScenePresenter = PresenterProvider.provideChannelScenePresenter();
    }

    @FXML
    public void initialize() {
        imageView.setPickOnBounds(true);
        imageView.setImage(channelScenePresenter.obtainChannel(ChannelSemaphore.getValue()));
    }
}
