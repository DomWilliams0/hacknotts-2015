package memes.game.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;

import java.util.BitSet;

public class InputHandler implements InputProviderListener {

    private static InputHandler INSTANCE = new InputHandler();

    private BitSet pressed, wasPressed;

    private InputHandler() {
    }

    public static void init(GameContainer game) {
        InputProvider provider = new InputProvider(game.getInput());
        provider.addListener(INSTANCE);

        for (InputKey key : InputKey.values())
            provider.bindCommand(key.getControl(), new MemeCommand(key));

        INSTANCE.pressed = new BitSet(InputKey.values().length);
        INSTANCE.wasPressed = new BitSet(InputKey.values().length);

    }

    // TODO: replace with startMoving/endMoving

    public static boolean isPressed(InputKey key) {
        return INSTANCE.pressed.get(key.ordinal());
    }

    public static boolean isFirstPressed(InputKey key) {
        return isPressed(key) && !INSTANCE.wasPressed.get(key.ordinal());
    }

    public static boolean isHeld(InputKey key) {
        return isPressed(key) && INSTANCE.wasPressed.get(key.ordinal());
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
        int key = ((MemeCommand) command).getKey().ordinal();
        wasPressed.set(key, pressed.get(key));
        pressed.set(key, nowPressed);
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
