package memes.game.world;

import memes.game.entity.PlayerEntity;
import memes.game.render.TileRenderer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum TileType {
    FLOOR(
            // Metadata factory
            TileMetadata.FloorMetadata::new,
            24, 0
    ),
    DESK(6, 5),
    WALL(24, 13),
    COFFEE_MACHINE(
            TileMetadata.CoffeeMachineMetadata::new,
            (tile, player) -> {
                TileMetadata.CoffeeMachineMetadata meta = (TileMetadata.CoffeeMachineMetadata) tile.metadata;
                if (player.caffeineLevel < PlayerEntity.MAX_CAFFEINE) {
                    player.caffeineLevel++;
                    System.out.printf("%s just drank some poo water%n", player);
                }
            }
    ),
    COMPUTER(
            TileMetadata.ComputerMetadata::new,
            (tile, player) -> {
                TileMetadata.ComputerMetadata meta = (TileMetadata.ComputerMetadata) tile.metadata;
                if (meta.user == null) {
                    meta.user = player.getUsername();
                    System.out.printf("%s is now bound to the computer%n", player);
                } else {
                    if (meta.user.equals(player.getUsername())) {
                        meta.developmentProgress++;
                        System.out.printf("%s just programmed on the computer%n", player);
                    } else {
                        meta.developmentProgress--;
                        System.out.printf("%s just sabotaged the computer%n", player);
                    }
                }
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

    public static TileType getFromChar(char c) {
        switch (c) {
            case 'f':
                return TileType.FLOOR;
            case 'd':
                return TileType.DESK;
            case 'w':
                return TileType.WALL;
            case 'c':
                return TileType.COMPUTER;
            default:
                throw new IllegalArgumentException("Invalid tile char: " + c);
        }
    }

}
