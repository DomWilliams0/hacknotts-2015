package memes.gen;

import memes.world.World;

/**
 * Created by samtebbs on 28/11/2015.
 */
public interface Generator {

    World genWorld(int xSize, int ySize);

}
