package memes.game.input;

import memes.game.event.EventHandler;
import memes.game.event.IEventHandler;
import memes.game.event.InputEvent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;

public class InputHandler implements InputProviderListener {

    private EventHandler<InputEvent> eventHandler;

    public InputHandler(GameContainer game) {
        eventHandler = new EventHandler<>();

        InputProvider provider = new InputProvider(game.getInput());
        provider.addListener(this);

        for (InputKey key : InputKey.values())
            provider.bindCommand(key.getControl(), new MemeCommand(key));
    }

    public void addHandler(IEventHandler<InputEvent> handler) {
        eventHandler.addHandler(handler);
    }

    @Override
    public void controlPressed(Command command) {
        update(command, true);
    }

    @Override
    public void controlReleased(Command command) {
        update(command, false);
    }

    private void update(Command command, boolean nowPressed) {
        InputKey key = ((MemeCommand) command).getKey();
        eventHandler.callHandlers(new InputEvent(key, nowPressed));
    }

    private static class MemeCommand implements Command {
        private InputKey key;


        private MemeCommand(InputKey key) {
            this.key = key;
        }

        public InputKey getKey() {
            return key;
        }
    }
}
