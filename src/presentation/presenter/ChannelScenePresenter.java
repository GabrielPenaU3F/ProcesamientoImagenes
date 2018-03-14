package presentation.presenter;

import core.action.channels.ObtainRGBChannelAction;
import domain.RGBChannel;
import javafx.scene.image.Image;

public class ChannelScenePresenter {

    private ObtainRGBChannelAction obtainRGBChannelAction;

    public ChannelScenePresenter(ObtainRGBChannelAction obtainRGBChannelAction) {
        this.obtainRGBChannelAction = obtainRGBChannelAction;
    }

    public Image obtainRGBChannel(RGBChannel value) {
        return obtainRGBChannelAction.execute(value);
    }
}