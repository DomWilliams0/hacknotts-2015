package memes.game.render.anim;

import memes.game.entity.HumanEntity;
import memes.util.Constants;
import memes.util.Direction;
import memes.util.Point;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import java.util.HashMap;
import java.util.Map;

public class HumanEntityRenderer {

    private static final Color NAME_BOX_COLOUR = new Color(0.17f, 0.17f, 0.19f, 0.9f);
    private static final int TEMP_ANIM_STEP = 140;

    private Map<Direction, Animation> anims;
    private boolean playing, wasPlaying;

    private HumanEntity entity;
    private boolean debug;

    public HumanEntityRenderer(HumanEntity humanEntity) {
        this.entity = humanEntity;

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

    public HumanEntityRenderer setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }
    public Animation getAnimation(Direction direction) {
        direction = direction.toRightAngle();
        Animation anim = this.anims.get(direction);
        playing = entity.isPlayingAnimation();

        if (playing != wasPlaying) {
            anims.values().stream().forEach(playing ? Animation::start : Animation::stop);
            anim.setCurrentFrame(0);
        }

        wasPlaying = playing;

        return anim;
    }

    public void render(Graphics graphics) {
        render(graphics, entity.getPixelPosition());
    }

    public void render(Graphics graphics, Point pixelPosition) {
        graphics.pushTransform();
        graphics.translate((float) Constants.WINDOW_SIZE.getX() / 2, (float) Constants.WINDOW_SIZE.getY() / 2);

        graphics.pushTransform();
        graphics.translate(-Constants.SPRITESHEET_HUMAN_SIZE / 2, -Constants.SPRITESHEET_HUMAN_SIZE * 4 / 5);

        // Draw Entity
        Animation anim = getAnimation(entity.getMovementDirection());
        anim.draw((float) pixelPosition.getX(), (float) pixelPosition.getY());

        // Draw Username
        int width = graphics.getFont().getWidth(entity.getUsername());
        int height = graphics.getFont().getHeight(entity.getUsername());
        final int paddingH = 6;
        final int paddingV = 3;

        float nameX = (float) pixelPosition.getX() - width / 2 + entity.getBoundingBox().getWidth() / 2;
        float nameY = (float) pixelPosition.getY() - height / 2 - entity.getBoundingBox().getHeight() * 0.4f;

        graphics.setColor(NAME_BOX_COLOUR);
        graphics.fillRect(nameX - paddingH / 2, nameY - paddingV / 2, width + paddingH, height + paddingV);

        graphics.setColor(Color.white);
        graphics.drawString(entity.getUsername(), nameX, nameY);

        Point pos = entity.getPixelPosition();
        if (debug) {

            graphics.pushTransform();
            graphics.translate((float) pos.getX(), (float) pos.getY());

            Shape boundingBox = entity.getBoundingBox();
            graphics.setColor(Color.green);
            graphics.drawRect(0, 0, boundingBox.getWidth(), boundingBox.getHeight());

            graphics.popTransform();
        }

        graphics.popTransform();

        if (debug) {
            graphics.setColor(Color.magenta);
            graphics.drawOval(pos.getIntX() - 1, pos.getIntY() - 1, 2, 2);
        }

        graphics.popTransform();
    }

    public HumanEntity getEntity() {
        return entity;
    }
}
