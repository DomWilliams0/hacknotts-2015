package memes.game.entity;

import memes.game.event.*;
import memes.game.input.InputKey;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.util.Direction;
import memes.util.Point;

import java.util.BitSet;

public class PlayerEntity extends HumanEntity implements IEventHandler<Packet> {

    private EventHandler<MoveEvent> movementHandlers;
    private BitSet currentDirections;

    public PlayerEntity(Point position, String username) {
        super(position, username);
        movementHandlers = new EventHandler<>();
        currentDirections = new BitSet(InputKey.values().length);
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
    public void onEvent(Packet e) {
        switch (e.getPacketType()) {
            case Input: {
                InputEvent event = (InputEvent) e;
                InputKey key = event.getKey();
                if(key.getDirection() != null) {
                    boolean pressed = event.isPressed();

                    currentDirections.set(key.ordinal(), pressed);

                    // stop
                    if (currentDirections.isEmpty())
                        stopMoving();
                    else {
                        int i = 0;
                        Direction d = getMovementDirection();

                        while (true) {
                            i = currentDirections.nextSetBit(i + 1);
                            if (i < 0)
                                break;

                            Direction newDir = Direction.values()[i];

                            if (d == null)
                                d = newDir;
                            else
                                d = d.combine(newDir);
                        }

                        changeDirection(d);
                    }

                    // very basic shameful movement :(
                    if (!pressed)
                        stopMoving();
                    else {

                        // parse direction
                        Direction d = key.getDirection();
                        startMoving(d);
                    }
                } else if(key == InputKey.ACTION) {

                }
                break;
            }
            case Action:
                ActionEvent event = (ActionEvent)e;
                event.tile.type.onAction.onAction(event.tile, event.player);
                break;
        }
    }

}
