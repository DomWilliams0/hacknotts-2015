package memes.game.render.anim;

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
        direction = direction.toRightAngle();
        Animation anim = this.anims.get(direction);

        if (playing != wasPlaying) {
            anims.values().stream().forEach(playing ? Animation::start : Animation::stop);
            anim.setCurrentFrame(0);
        }

        wasPlaying = playing;

        return anim;
    }

    public void stop() {
        playing = false;
    }

    public void start() {
        playing = true;
    }
}
