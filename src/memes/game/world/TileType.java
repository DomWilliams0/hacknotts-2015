package memes.game.world;

import memes.game.render.TileRenderer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum TileType {
    FLOOR(
            // Metadata factory
            TileMetadata.FloorMetadata::new,
            25, 0
    ),
    DESK(6, 5),
    WALL(23, 13),
    COMPUTER(
            // Metadata factory
            TileMetadata.ComputerMetadata::new,
            // onAction
            (tile, player) -> {
                TileMetadata.ComputerMetadata meta = (TileMetadata.ComputerMetadata) tile.metadata;
                meta.developmentProgress++;
                System.out.printf("%s dev for tile is now %d%n", player.toString(), meta.developmentProgress);
            }
    );

    // This defines how the tile type's metadata is constructed
    public TileMetadata.MetadataFactory factory;
    public TileRenderer renderer;
    public TileActionListener onAction;
    private Image img;

    TileType(TileMetadata.MetadataFactory factory, TileRenderer renderer, TileActionListener onAction, int spriteX, int spriteY) {
        this.factory = factory;
        this.img = Tile.sprites.getSprite(spriteX, spriteY);
        this.renderer = renderer;
        this.onAction = onAction;
    }

    TileType(TileMetadata.MetadataFactory factory, TileActionListener onAction, int spriteX, int spriteY) {
        this(factory, TileRenderer.standardRenderer, onAction, spriteX, spriteY);
    }

    TileType(TileMetadata.MetadataFactory factory, TileActionListener onAction) {
        this(factory, TileRenderer.standardRenderer, onAction, 0, 0);
        try {
            img = new Image("res/terrain/" + this.name().toLowerCase() + ".png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    TileType(TileMetadata.MetadataFactory factory, int spriteX, int spriteY) {
        this(factory, TileRenderer.standardRenderer, (tile, world) -> {
        }, spriteX, spriteY);
    }

    TileType(int spriteX, int spriteY) {
        this(null, spriteX, spriteY);
    }

    public void render(TileMetadata meta, float x, float y) {
        renderer.render(img, x, y);
    }

}
