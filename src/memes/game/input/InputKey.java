package memes.game.input;

import memes.util.Direction;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.KeyControl;

public enum InputKey {
    UP(Input.KEY_W, Direction.NORTH),
    LEFT(Input.KEY_A, Direction.WEST),
    RIGHT(Input.KEY_D, Direction.EAST),
    DOWN(Input.KEY_S, Direction.SOUTH),

    ACTION(Input.KEY_E),
    SPACE(Input.KEY_SPACE),
    EXIT(Input.KEY_ESCAPE);

    public static final InputKey[] DIRECTIONALS = {UP, LEFT, RIGHT, DOWN};

    private KeyControl control;
    private Direction direction;

    InputKey(int key, Direction direction) {
        this.control = new KeyControl(key);
        this.direction = direction;
    }
    
    InputKey(int key) {
        this(key, null);
    }

    public KeyControl getControl() {
        return control;
    }

    public Direction getDirection() {
        return direction;
    }
}
