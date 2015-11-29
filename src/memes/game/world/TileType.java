package memes.game.world;

import memes.game.entity.PlayerEntity;
import memes.game.render.TileRenderer;

import java.io.Serializable;

public enum TileType implements Serializable {
    CHAIR(
            TileMetadata.ChairMetadata::new,
            (tile, player, sabotage) -> {
                TileMetadata.ChairMetadata meta = (TileMetadata.ChairMetadata) tile.metadata;
                if(sabotage) {
                    if(!meta.sabotaged) {
                        meta.sabotaged = true;
                        System.out.printf("%s sabotaged a chair%n", player);
                        meta.world.update();
                    }
                } else if (meta.sabotaged) {
                    if(player.relaxationLevel < PlayerEntity.MAX_RELAXATION) player.relaxationLevel++;
                    System.out.printf("%s just sat on a chair%n", player);
                    meta.world.update();
                } else {
                    if(player.relaxationLevel > 0) player.relaxationLevel--;
                    meta.sabotaged = false;
                    System.out.printf("%s just sat down on a sabotaged chair!%n", player);
                    meta.world.update();
                }
            },
            0, 7
    ),
    FLOOR(
            // Metadata factory
            TileMetadata.FloorMetadata::new,
            24, 0
    ),
    DESK(6, 5),
    WALL(24, 13),
    COFFEE_MACHINE(
            TileMetadata.CoffeeMachineMetadata::new,
            (tile, player, sabotage) -> {
                TileMetadata.CoffeeMachineMetadata meta = (TileMetadata.CoffeeMachineMetadata) tile.metadata;
                if (sabotage) {
                    if (!meta.sabotaged) {
                        meta.sabotaged = true;
                        System.out.printf("%s just sabotaged a coffee machine%n", player);
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
                    System.out.printf("%s is now bound to a computer%n", player);
                    meta.world.update();
                    return;
                }

                // programming on own computer
                if (meta.user.equals(player.getUsername())) {
                    if(player.caffeineLevel >= 0) {
                        meta.developmentProgress += player.relaxationLevel;
                        player.relaxationLevel--;
                        System.out.printf("%s just programmed on a computer%n", player);
                        meta.world.update();
                    } else System.out.printf("%s tried to program but is too tired!%n", player);
                    return;
                }

                // sabotaging
                if (sabotage) {
                    meta.developmentProgress--;
                    System.out.printf("%s just sabotaged a computer%n", player);
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
    public int spriteX, spriteY;
    public String name = name().toLowerCase();

    TileType(TileMetadata.MetadataFactory factory, TileRenderer renderer, TileActionListener onAction, int spriteX, int spriteY) {
        this.factory = factory;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
        this.renderer = renderer;
        this.onAction = onAction;
    }

    TileType(TileMetadata.MetadataFactory factory, TileActionListener onAction, int spriteX, int spriteY) {
        this(factory, TileRenderer.standardRenderer, onAction, spriteX, spriteY);
    }

    TileType(TileMetadata.MetadataFactory factory, TileActionListener onAction) {
        this(factory, TileRenderer.standardRenderer, onAction, -1, -1);
    }

    TileType(TileMetadata.MetadataFactory factory, int spriteX, int spriteY) {
        this(factory, TileRenderer.standardRenderer, (tile, world, flag) -> {
        }, spriteX, spriteY);
    }

    TileType(int spriteX, int spriteY) {
        this(null, spriteX, spriteY);
    }

    public void render(TileMetadata meta, float x, float y) {
        //renderer.render(img, x, y);
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
            case 'b':
                return TileType.CHAIR;
            default:
                throw new IllegalArgumentException("Invalid tile char: " + ch);
        }
    }

}
