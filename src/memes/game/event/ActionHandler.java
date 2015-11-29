package memes.game.event;

import memes.game.entity.PlayerEntity;
import memes.game.input.InputKey;
import memes.net.packet.Packet;
import memes.util.Point;

public class ActionHandler implements IEventHandler {

    @Override
    public void onEvent(Packet event) {
        switch(event.getPacketType()) {
            case Input:
                InputEvent e = (InputEvent)event;
                PlayerEntity player = e.getPlayer();
                Point p = player.getTilePosition();
                boolean keyFlag = false;
                if(e.getKey() == InputKey.SPACE) keyFlag = true;
                ActionEvent action = new ActionEvent(player, player.getWorld().getTile(p.getIntX(), p.getIntY()).get(), keyFlag);
            case Action:
                ActionEvent e = (ActionEvent)event;
                e.tile.type.onAction.onAction(e.tile, e.player, e.flag);
                break;
        }
    }

}
