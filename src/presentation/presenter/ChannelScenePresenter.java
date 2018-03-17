package presentation.presenter;

import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import domain.Channel;
import javafx.scene.image.Image;

public class ChannelScenePresenter {

    private ObtainRGBChannelAction obtainRGBChannelAction;
    private ObtainHSVChannelAction obtainHSVChannelAction;

    public ChannelScenePresenter(ObtainRGBChannelAction obtainRGBChannelAction, ObtainHSVChannelAction obtainHSVChannelAction) {
        this.obtainRGBChannelAction = obtainRGBChannelAction;
        this.obtainHSVChannelAction = obtainHSVChannelAction;
    }

    public Image obtainChannel(Channel channel) {
        if (channel == Channel.RED || channel == Channel.GREEN || channel == Channel.BLUE) {
            return obtainRGBChannelAction.execute(channel);
        } else {
            return obtainHSVChannelAction.execute(channel);
        }
    }
}