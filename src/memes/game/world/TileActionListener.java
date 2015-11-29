package memes.game.world;

import memes.game.entity.PlayerEntity;

/**
 * Created by samtebbs on 29/11/2015.
 */
public interface TileActionListener {

    public void onAction(Tile tile, PlayerEntity player);

}
