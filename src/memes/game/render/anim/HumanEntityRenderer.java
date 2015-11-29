package memes.game.render.anim;

import memes.game.entity.HumanEntity;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.HashMap;
import java.util.Map;

public class HumanEntityRenderer {

    private static final Color NAME_BOX_COLOUR = new Color(0.17f, 0.17f, 0.19f, 0.9f);
    private static final int TEMP_ANIM_STEP = 140;

    private Map<Direction, Animation> anims;
    private boolean playing, wasPlaying;

    private HumanEntity humanEntity;

    public HumanEntityRenderer(HumanEntity humanEntity) {
        this.humanEntity = humanEntity;

        Animation[] animations = Animations.createAnimations("business_man", TEMP_ANIM_STEP);
        if (animations == null)
            throw new IllegalStateException("Animation has not been loaded");

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
        playing = humanEntity.isPlayingAnimation();

        if (playing != wasPlaying) {
            anims.values().stream().forEach(playing ? Animation::start : Animation::stop);
            anim.setCurrentFrame(0);
        }

        wasPlaying = playing;

        return anim;
    }

    public void render(Graphics graphics) {
        render(graphics, humanEntity.getPixelPosition());
    }

    public void render(Graphics graphics, Point pixelPosition) {
        // player
        Animation anim = getAnimation(humanEntity.getMovementDirection());
        anim.draw((float) pixelPosition.getX(), (float) pixelPosition.getY());

        // name
        int width = graphics.getFont().getWidth(humanEntity.getUsername());
        int height = graphics.getFont().getHeight(humanEntity.getUsername());
        final int paddingH = 6;
        final int paddingV = 3;

        float nameX = (float) pixelPosition.getX() - width / 2 + humanEntity.getAABB().getWidth() / 2;
        float nameY = (float) pixelPosition.getY() - height / 2 - humanEntity.getAABB().getHeight() * 0.4f;

        graphics.setColor(NAME_BOX_COLOUR);
        graphics.fillRect(nameX - paddingH / 2, nameY - paddingV / 2, width + paddingH, height + paddingV);

        graphics.setColor(Color.white);
        graphics.drawString(humanEntity.getUsername(), nameX, nameY);
    }

    public HumanEntity getHumanEntity() {
        return humanEntity;
    }
}
