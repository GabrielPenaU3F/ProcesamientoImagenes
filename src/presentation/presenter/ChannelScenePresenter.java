package presentation.presenter;

import core.action.channels.ObtainHSVChannelAction;
import core.action.channels.ObtainRGBChannelAction;
import domain.Channel;
import javafx.scene.image.Image;

public class ChannelScenePresenter {

    private final ObtainRGBChannelAction obtainRGBChannelAction;
    private final ObtainHSVChannelAction obtainHSVChannelAction;

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