package memes.game.entity;

import memes.game.event.EventHandler;
import memes.game.event.IEventHandler;
import memes.game.event.InputEvent;
import memes.game.event.MoveEvent;
import memes.game.input.InputKey;
import memes.net.packet.PacketType;
import memes.util.Direction;
import memes.util.Point;

public class PlayerEntity extends HumanEntity implements IEventHandler<InputEvent> {

    private EventHandler<MoveEvent> movementHandlers;

    public PlayerEntity(Point position, String username) {
        super(position, username);
        movementHandlers = new EventHandler<>();
    }

    public void addHandler(IEventHandler<MoveEvent> handler) {
        movementHandlers.addHandler(handler);
    }

    @Override
    public void startMoving(Direction direction, int speed) {
        super.startMoving(direction, speed);
        movementHandlers.callHandlers(new MoveEvent(this, PacketType.StartMove));
    }

    @Override
    public void stopMoving() {
        super.stopMoving();
        movementHandlers.callHandlers(new MoveEvent(this, PacketType.EndMove));
    }

    @Override
    public void onEvent(InputEvent event) {
        InputKey key = event.getKey();
        boolean pressed = event.isPressed();

        // very basic shameful movement :(
        if (!pressed)
            stopMoving();
        else {

            // parse direction
            Direction d = key.getDirection();
            startMoving(d);
        }
    }
}
