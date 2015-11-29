package memes.game.event;

import memes.Game;
import memes.game.entity.PlayerEntity;
import memes.game.input.InputKey;
import memes.game.world.World;
import memes.net.packet.Packet;
import memes.util.Point;

public class ActionHandler implements IEventHandler {

    @Override
    public void onEvent(Packet event) {
        switch(event.getPacketType()) {
            case Input:
                InputEvent e = (InputEvent)event;
                World world = Game.INSTANCE.world;
                PlayerEntity player = (PlayerEntity) world.getEntityFromID(e.getPlayerID());
                Point p = player.getTilePosition();
                boolean keyFlag = false;
                if(e.getKey() == InputKey.SPACE) keyFlag = true;
                ActionEvent action = new ActionEvent(player, world.getTile(p.getIntX(), p.getIntY()).get(), keyFlag);
            case Action:
                ActionEvent e = (ActionEvent)event;
                e.tile.type.onAction.onAction(e.tile, e.player, e.flag);
                break;
        }
    }

}
