package memes.game.event;

import memes.GameClient;
import memes.game.entity.PlayerEntity;
import memes.game.input.InputKey;
import memes.game.world.World;
import memes.net.packet.Packet;
import memes.util.Point;

public class ActionHandler implements IEventHandler {
    private PlayerEntity player;

    public ActionHandler(PlayerEntity player) {

        this.player = player;
    }

    @Override
    public void onEvent(Packet event) {
        switch (event.getPacketType()) {
            case Input:
                InputEvent e = (InputEvent) event;
                World world = GameClient.INSTANCE.getWorld();
                Point p = player.getTilePosition();
                boolean keyFlag = false;
                if (e.getKey() == InputKey.SPACE) keyFlag = true;
//                ActionEvent action = new ActionEvent(player, world.getTile(p.getIntX(), p.getIntY()).get(), keyFlag);
                break;
            case Action:
                ActionEvent actionEvent = (ActionEvent) event;
                actionEvent.tile.type.onAction.onAction(actionEvent.tile, actionEvent.player, actionEvent.flag);
                break;
        }
    }

}
