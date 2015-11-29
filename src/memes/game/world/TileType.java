package memes.game.world;

import memes.game.entity.PlayerEntity;
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
    COFFEE_MACHINE(
            TileMetadata.CoffeeMachineMetadata::new,
            (tile, player, sabotage) -> {
                TileMetadata.CoffeeMachineMetadata meta = (TileMetadata.CoffeeMachineMetadata) tile.metadata;
                if (sabotage) {
                    if (!meta.sabotaged) {
                        meta.sabotaged = true;
                        System.out.printf("%s just sabotaged the coffee machine%n", player);
                        meta.world.update();
                    }
                } else if (player.caffeineLevel < PlayerEntity.MAX_CAFFEINE) {
                    if (meta.sabotaged) {
                        player.caffeineLevel = Math.max(player.caffeineLevel - 10, 0);
                        meta.sabotaged = false;
                        System.out.printf("%s just drank a shit coffee%n", player);
                        meta.world.update();
                    } else {
                        player.caffeineLevel++;
                        System.out.printf("%s just drank some coffee%n", player);
                        meta.world.update();
                    }
                }
            }
    ),
    COMPUTER(
            TileMetadata.ComputerMetadata::new,
            (tile, player, sabotage) -> {
                TileMetadata.ComputerMetadata meta = (TileMetadata.ComputerMetadata) tile.metadata;

                // no current user
                if (meta.user == null) {
                    meta.user = player.getUsername();
                    player.computer = meta;
                    System.out.printf("%s is now bound to the computer%n", player);
                    meta.world.update();
                    return;
                }

                // programming on own computer
                if (meta.user.equals(player.getUsername())) {
                    meta.developmentProgress++;
                    System.out.printf("%s just programmed on the computer%n", player);
                    meta.world.update();
                    return;
                }

                // sabotaging
                if (sabotage) {
                    meta.developmentProgress--;
                    System.out.printf("%s just sabotaged the computer%n", player);
                    meta.world.update();
                    return;
                }

                // bastard
                if (player.computer != null) {
                    player.computer.developmentProgress++;
                    System.out.printf("%s just stole some code! (sneaky bugger)%n", player);
                    meta.world.update();
                    return;
                }

                System.out.printf("%s is trying to steal code but they haven't been assigned a computer yet%n", player);
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
        this(factory, TileRenderer.standardRenderer, (tile, world, flag) -> {
        }, spriteX, spriteY);
    }

    TileType(int spriteX, int spriteY) {
        this(null, spriteX, spriteY);
    }

    public void render(TileMetadata meta, float x, float y) {
        renderer.render(img, x, y);
    }

    public static TileType getFromChar(char ch) {
        switch (ch) {
            case 'w':
                return TileType.WALL;
            case 'f':
                return TileType.FLOOR;
            case 'c':
                return TileType.COMPUTER;
            case 'm':
                return TileType.COFFEE_MACHINE;
            case 'd':
                return TileType.DESK;
            default:
                throw new IllegalArgumentException("Invalid tile char: " + ch);
        }
    }

}
