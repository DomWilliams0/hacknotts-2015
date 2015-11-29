package memes.game.entity;

import memes.game.event.*;
import memes.game.input.InputKey;
import memes.net.packet.Packet;
import memes.net.packet.PacketType;
import memes.util.Direction;
import memes.util.Point;

import java.util.BitSet;

public class PlayerEntity extends HumanEntity implements IEventHandler {

    private EventHandler<MoveEvent> movementHandlers;
    private BitSet currentDirections;

    public PlayerEntity(Point position, String username) {
        super(position, username);
        movementHandlers = new EventHandler<>();
        currentDirections = new BitSet(InputKey.values().length);
    }

    public void addHandler(IEventHandler handler) {
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
            // input
            case Input: {
                InputEvent event = (InputEvent) e;
                InputKey key = event.getKey();
                if (key.getDirection() != null) {
                    boolean pressed = event.isPressed();

                    currentDirections.set(key.getDirection().ordinal(), pressed);

                    // stop
                    if (currentDirections.isEmpty())
                        stopMoving();
                    else {

                        // add all active directions
                        Direction[] directions = new Direction[currentDirections.cardinality()];
                        int i = 0;
                        for (int bit = currentDirections.nextSetBit(0); bit != -1; bit = currentDirections.nextSetBit(bit + 1))
                            directions[i++] = Direction.values()[bit];

                        Direction currentDirection = getMovementDirection();
                        Direction newDirection = currentDirection.combine(directions);

                        if (newDirection == null)
                            stopMoving();
                        else
                            startMoving(newDirection);
                    }
                }
                break;
            }
            case Action:
                ActionEvent event = (ActionEvent) e;
                event.tile.type.onAction.onAction(event.tile, event.player);
                break;
        }
    }

}
