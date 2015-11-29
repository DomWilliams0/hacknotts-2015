package memes.game.world;

import java.io.Serializable;

/**
 * Represents metadata that is specific to tile types, and belongs to a tile object.
 * This could store the amount of coffee left in a specific tile instance,
 * or the development progress of a certain computer
 */
public abstract class TileMetadata implements Serializable {

    public interface MetadataFactory {

        TileMetadata getMetadata(int x, int y, World world);

    }

    public World world;
    public int x, y;

    public TileMetadata(int x, int y, World world) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    public static class FloorMetadata extends TileMetadata {

        public boolean a, b, c, d;

        public FloorMetadata(int x, int y, World world) {
            super(x, y, world);
        }

    }

    public static class ComputerMetadata extends TileMetadata {

        public int developmentProgress = 0;
        public String user;
        public static final int THRESHOLD = 10;

        public ComputerMetadata(int x, int y, World world) {
            super(x, y, world);
        }

    }

    public static class CoffeeMachineMetadata extends TileMetadata {

        public boolean sabotaged;

        public CoffeeMachineMetadata(int x, int y, World world) {
            super(x, y, world);
        }

    }

}