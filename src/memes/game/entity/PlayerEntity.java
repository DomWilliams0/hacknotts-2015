package memes.game.entity;

import memes.game.event.EventHandler;
import memes.game.event.IEventHandler;
import memes.game.event.MoveEvent;
import memes.util.Point;

public class PlayerEntity extends HumanEntity {

    private EventHandler<MoveEvent> movementHandlers;

    public PlayerEntity(Point position, String username) {
        super(position, username);
    }

    public void addHandler(IEventHandler<MoveEvent> handler) {
        movementHandlers.addHandler(handler);
    }
}
