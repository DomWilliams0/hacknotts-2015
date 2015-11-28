package memes.gen;

import memes.world.World;

public interface Generator {

    World genWorld(int xSize, int ySize);

}
