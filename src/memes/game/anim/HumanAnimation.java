package memes.game.anim;

import memes.util.Direction;
import org.newdawn.slick.Animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper wrapper around animations for the 4 main directions of humans
 */
public class HumanAnimation {

    private static final int TEMP_ANIM_STEP = 140;

    private Map<Direction, Animation> anims;

    public HumanAnimation(String animationNickname) {

        Animation[] animations = Animations.createAnimations(animationNickname, TEMP_ANIM_STEP);
        if (animations == null)
            throw new IllegalStateException("Animation '" + animationNickname + "' has not been loaded");

        anims = new HashMap<>();
        anims.put(Direction.SOUTH, animations[0]);
        anims.put(Direction.WEST, animations[1]);
        anims.put(Direction.EAST, animations[2]);
        anims.put(Direction.NORTH, animations[3]);
    }

    public Animation getAnimation(Direction direction) {
        Animation anim = this.anims.get(direction);
        if (anim == null)
            throw new IllegalArgumentException("Direction must be N, E, S or W");

        return anim;
    }
}
