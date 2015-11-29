package memes.game.world.gen;

import memes.game.world.World;

public interface Generator {

    World genWorld(int xSize, int ySize);

}
