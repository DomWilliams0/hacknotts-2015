package memes.game.anim;

import memes.util.Constants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class SpriteSheets {

    private static SpriteSheets INSTANCE = new SpriteSheets();

    private Map<String, SpriteSheet> sheets;

    // no instantiation pls
    private SpriteSheets() {
        sheets = new TreeMap<>();
    }

    public static void loadAll(File directory) {
        // watch ur input
        if (!directory.isDirectory())
            throw new IllegalArgumentException("Must be directory");

        // todo stop getting distracted

    }

    public static void load(String fileName, String nickName) {
        // already loaded
        if (INSTANCE.sheets.containsKey(nickName))
            return;

        Path file = Paths.get(Constants.SPRITESHEET_PATH, fileName);
        if (!file.toFile().exists())
            System.err.println("Spritesheet '" + file.toAbsolutePath() + "' does not exist");

        try {
            SpriteSheet spriteSheet = new SpriteSheet(file.toString(), Constants.SPRITESHEET_HUMAN_SIZE, Constants.SPRITESHEET_HUMAN_SIZE);
            INSTANCE.sheets.put(nickName, spriteSheet);
            System.out.println("Loaded spritesheet '" + nickName + "'");

        } catch (SlickException e) {
            System.err.println("Could not load spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Animation createAnimation(String nickName) {
        SpriteSheet sheet = INSTANCE.sheets.get(nickName);

        // not loaded
        if (sheet == null)
            return null;

//        return new Animation(sheet, 0, 0, 0, 0, true, 0, true);
        throw new UnsupportedOperationException("pls wait");
    }

}
