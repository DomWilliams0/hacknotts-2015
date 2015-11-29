package memes.game.entity;

import memes.game.event.*;
import memes.game.input.InputKey;
import memes.game.world.TileMetadata;
import memes.net.packet.Packet;
import memes.util.Direction;
import memes.util.Point;

import java.io.Serializable;
import java.util.BitSet;

public class PlayerEntity extends HumanEntity implements IEventHandler, Serializable {

    private EventHandler<MoveEvent> movementHandlers;
    private BitSet currentDirections;
    public static final int MAX_CAFFEINE = 50, MAX_RELAXATION = 50;
    public int caffeineLevel = MAX_CAFFEINE, relaxationLevel = MAX_RELAXATION;
    public TileMetadata.ComputerMetadata computer;

    public PlayerEntity(long entityID, String username, Point position) {
        super(entityID, position, username);
        movementHandlers = new EventHandler<>();
        currentDirections = new BitSet(InputKey.DIRECTIONALS.length);
    }

    public void addHandler(IEventHandler handler) {
        movementHandlers.addHandler(handler);
    }

    @Override
    public void startMoving(Direction direction, int speed) {
        super.startMoving(direction, speed);
        movementHandlers.callHandlers(new MoveEvent(this, true));
    }

    @Override
    public void stopMoving() {
        super.stopMoving();
        movementHandlers.callHandlers(new MoveEvent(this, false));
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
                event.tile.type.onAction.onAction(event.tile, event.player, false);
                break;
        }
    }

}
