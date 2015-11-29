package memes.game.event;

import memes.net.packet.Packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventHandler<E extends Packet> implements Serializable {

    private List<IEventHandler> handlers;

    public EventHandler() {
        handlers = new ArrayList<>();
    }

    public void addHandler(IEventHandler handler) {
        if (handler != null)
            handlers.add(handler);
    }

    public void callHandlers(E event) {
        handlers.stream().forEach(e -> e.onEvent(event));
    }

}
