package memes.game.render.anim;

import memes.util.Constants;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class Animations {

    private static Animations INSTANCE = new Animations();

    private Map<String, SpriteSheet> sheets;

    // no instantiation pls
    private Animations() {
        sheets = new TreeMap<>();
    }

    public static void loadAll() {
        loadAll(new File(Constants.SPRITESHEET_PATH));
    }

    public static void loadAll(File directory) {
        // watch ur input
        if (!(directory.exists() && directory.isDirectory()))
            throw new IllegalArgumentException("Uh oh invalid directory");

        try {
            Files.newDirectoryStream(directory.toPath(), "*.png").forEach((path -> {
                String name = path.getFileName().toString();
                int extIndex = name.lastIndexOf('.');
                if (extIndex >= 0)
                    name = name.substring(0, extIndex);

                load(path.getFileName().toString(), name);

            }));
        } catch (IOException e) {
            System.err.println("Could not load spritesheets from " + directory.getPath());
            e.printStackTrace();
        }

    }

    public static void load(String fileName, String nickname) {
        // already loaded
        if (INSTANCE.sheets.containsKey(nickname))
            return;

        Path file = Paths.get(Constants.SPRITESHEET_PATH, fileName);
        if (!file.toFile().exists())
            System.err.println("Spritesheet '" + file.toAbsolutePath() + "' does not exist");

        try {
            SpriteSheet spriteSheet = new SpriteSheet(file.toString(), Constants.SPRITESHEET_HUMAN_SIZE, Constants.SPRITESHEET_HUMAN_SIZE);
            INSTANCE.sheets.put(nickname, spriteSheet);
            System.out.println("Loaded spritesheet '" + nickname + "'");

        } catch (SlickException e) {
            System.err.println("Could not load spritesheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Animation[] createAnimations(String nickname, int duration) {
        SpriteSheet sheet = INSTANCE.sheets.get(nickname);

        // not loaded
        if (sheet == null)
            return null;

        Animation[] anims = new Animation[sheet.getVerticalCount()];

        // load each animation
        for (int i = 0; i < anims.length; i++) {
            anims[i] = new Animation(sheet, 0, i,
                    sheet.getHorizontalCount() - 1,
                    i, true, duration, true);
            anims[i].stop();
        }

        return anims;
    }

}
