package memes.world;

import memes.render.TileRenderer;
import org.newdawn.slick.Image;

public enum TileType {
    FLOOR((x, y, world) -> new TileMetadata.FloorMetadata(x, y, world), 25, 0),
    DESK(6, 5),
    WALL(23, 13),
    COMPUTER((x, y, world) -> new TileMetadata.ComputerMetadata(x, y, world), 18, 0);

    // This defines how the tile type's metadata is constructed
    public TileMetadata.MetadataFactory factory;
    public TileRenderer renderer;
    private Image img;

    TileType(TileMetadata.MetadataFactory factory, TileRenderer renderer, int spriteX, int spriteY) {
        this.factory = factory;
        this.img = Tile.sprites.getSprite(spriteX, spriteY);
        this.renderer = renderer;
    }

    TileType(TileMetadata.MetadataFactory factory, int spriteX, int spriteY) {
        this(factory, TileRenderer.standardRenderer, spriteX, spriteY);
    }

    TileType(int spriteX, int spriteY) {
        this(null, spriteX, spriteY);
    }

    public void render(TileMetadata meta, float x, float y) {
        renderer.render(img, x, y);
    }
}
