package memes.gen;

import memes.world.Tile;
import memes.world.TileType;
import memes.world.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StaticOfficeGenerator implements Generator {

    @Override
    public World genWorld(int xSize, int ySize) {
        if (xSize < 0 || ySize < 0) throw new IllegalArgumentException("The int arguments cannot be less than 0");
        Tile[][] tiles = new Tile[xSize][ySize];
        try {
            Scanner in = new Scanner(new File("res/office.txt"));
            int x = 0, y = 0;
            while (in.hasNextLine()) {
                // Read line as chars and convert them to tiles
                char[] line = in.nextLine().toCharArray();
                if (line.length != xSize)
                    throw new IllegalStateException("Line length of office file is not equal to xSize");
                for (char aLine : line) {
                    tiles[y][x++] = new Tile(getTileType(aLine));
                }
                y++;
                // If we are at ySize, then stop reading the file
                if (y == ySize) break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        World world = new World(tiles, xSize, ySize);
        return world;
    }

    private TileType getTileType(char ch) {
        switch (ch) {
            case 'n':
                return TileType.FLOOR;
            case 'd':
                return TileType.DESK;
            case 'w':
                return TileType.WALL;
            case 'c':
                return TileType.COMPUTER;
            default:
                System.err.println("Unrecognised tile character: " + ch);
                return null;
        }
    }

}
