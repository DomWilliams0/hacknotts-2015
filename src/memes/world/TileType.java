package memes.world;

import memes.render.TileRenderer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum TileType {
    FLOOR((x, y, world) -> new TileMetadata.FloorMetadata(x, y, world)),
    DESK,
    WALL,
    COMPUTER((x, y, world) -> new TileMetadata.ComputerMetadata(x, y, world));

    // This defines how the tile type's metadata is constructed
    public TileMetadata.MetadataFactory factory;
    public TileRenderer renderer;
    private Image img;

    TileType(TileMetadata.MetadataFactory factory, TileRenderer renderer) {
        this.factory = factory;
        try {
            this.img = new Image("res/terrain/" + this.name().toLowerCase() + ".png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.renderer = renderer;
    }

    TileType(TileMetadata.MetadataFactory factory) {
        this(factory, TileRenderer.standardRenderer);
    }

    TileType() {
        this(null);
    }

    public void render(TileMetadata meta, float x, float y) {
        renderer.render(img, x, y);
    }
}
