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

    private boolean playing, wasPlaying;

    public HumanAnimation(String animationNickname) {

        Animation[] animations = Animations.createAnimations(animationNickname, TEMP_ANIM_STEP);
        if (animations == null)
            throw new IllegalStateException("Animation '" + animationNickname + "' has not been loaded");

        anims = new HashMap<>();
        anims.put(Direction.SOUTH, animations[0]);
        anims.put(Direction.WEST, animations[1]);
        anims.put(Direction.EAST, animations[2]);
        anims.put(Direction.NORTH, animations[3]);

        playing = wasPlaying = false;
    }

    public Animation getAnimation(Direction direction) {
        Animation anim = this.anims.get(direction);
        if (anim == null)
            throw new IllegalArgumentException("Direction must be N, E, S or W");

        if (playing != wasPlaying) {
            if (playing)
                anim.start();
            else anim.stop();

            anim.setCurrentFrame(0);
            wasPlaying = playing;
        }

        return anim;
    }

    public void stop() {
        playing = false;
    }

    public void start() {
        playing = true;
    }
}
