package memes.game.world.gen;

import memes.game.world.Tile;
import memes.game.world.TileType;
import memes.game.world.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class StaticOfficeGenerator implements Generator {

    @Override
    public World genWorld(int xSize, int ySize) {
        Tile[][] tiles = null;
        try {
            Scanner in = new Scanner(new File("res/office.txt"));
            xSize = Integer.MAX_VALUE;
            LinkedList<String> lines = new LinkedList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                xSize = Math.min(xSize, line.length());
                lines.add(line);
            }
            ySize = lines.size();
            tiles = new Tile[xSize][ySize];
            for(int y = 0; y < ySize; y++) {
                char[] line = lines.get(y).toCharArray();
                for(int x = 0; x < xSize; x++) tiles[x][y] = new Tile(getTileType(line[x]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new World(tiles, xSize, ySize);
    }

    private TileType getTileType(char ch) {
        switch (ch) {
            case 'f':
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
