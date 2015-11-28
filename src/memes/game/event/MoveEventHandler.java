package memes.game.event;

public interface MoveEventHandler implements EventHandler<MoveEvent> {
    /**
     * Handles both start and end moving
     */
    void onMove(MoveEvent e);
}
