package memes.game.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.KeyControl;

public enum InputKey {
    UP(Input.KEY_W),
    LEFT(Input.KEY_A),
    RIGHT(Input.KEY_D),
    DOWN(Input.KEY_S);

    private KeyControl control;

    InputKey(int key) {
        this.control = new KeyControl(key);
    }

    public KeyControl getControl() {
        return control;
    }
}
