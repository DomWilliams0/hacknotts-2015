package memes.game.render;

import memes.util.Constants;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class TextureManager {

    public static SpriteSheet sprites = null;
    public static HashMap<String, Image> imageMap = new HashMap<>();

    static {
        try {
            sprites = new SpriteSheet(new Image("res/terrain/sheet/interior_transparent.png"),
                    Constants.TILE_LOAD_SIZE, Constants.TILE_LOAD_SIZE, 1, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private TextureManager() {
    }

    private static void load(File f) {
        String name = f.getName();
        System.out.println("loading file name = " + name);
        try {
            int extIndex = name.lastIndexOf('.');
            if (extIndex != -1)
                name = name.substring(0, extIndex);

            imageMap.put(name, new Image(f.getAbsolutePath()));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            Files.newDirectoryStream(Paths.get("res/terrain"), "*.png").forEach(file -> load(file.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
