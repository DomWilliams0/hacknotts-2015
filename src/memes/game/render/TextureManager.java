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
            sprites = new SpriteSheet(new Image("res/terrain/interior_transparent.png"),
                    Constants.TILE_LOAD_SIZE, Constants.TILE_LOAD_SIZE, 1, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void load(File f) {
        String name = f.getName();
        try {
            imageMap.put(name.substring(name.lastIndexOf('/') + 1, name.indexOf('.')), new Image(f.getName()));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Files.newDirectoryStream(Paths.get("res/terrain"), name -> name.endsWith(".png")).forEach(file -> load(file.toFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
