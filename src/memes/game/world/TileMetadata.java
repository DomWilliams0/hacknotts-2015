package memes.game.world;

/**
 * Represents metadata that is specific to tile types, and belongs to a tile object.
 * This could store the amount of coffee left in a specific tile instance,
 * or the development progress of a certain computer
 */
public abstract class TileMetadata {

    public interface MetadataFactory {

        TileMetadata getMetadata(int x, int y, World world);

    }

    public static class FloorMetadata extends TileMetadata {

        public boolean a, b, c, d;

        public FloorMetadata(int x, int y, World world) {

        }

    }

    public static class ComputerMetadata extends TileMetadata {

        public int developmentProgress = 0;
        public String user;
        public static final int threshold = 10;

        public ComputerMetadata(int x, int y, World world) {

        }

    }

    public static class CoffeeMachineMetadata extends TileMetadata {

        public CoffeeMachineMetadata(int x, int y, World world) {

        }

    }

}