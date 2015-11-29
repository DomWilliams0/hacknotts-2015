package memes.game.event;

import memes.net.packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class EventHandler<E extends Packet> {

    private List<IEventHandler<E>> handlers;

    public EventHandler() {
        handlers = new ArrayList<>();
    }

    public void addHandler(IEventHandler<E> handler) {
        handlers.add(handler);
    }

    public void callHandlers(E event) {
        handlers.stream().forEach(e -> e.onEvent(event));
    }

}
