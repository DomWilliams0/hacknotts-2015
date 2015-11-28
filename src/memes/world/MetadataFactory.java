package memes.world;

public interface MetadataFactory {

    TileMetadata getMetadata(int x, int y, World world);

}
