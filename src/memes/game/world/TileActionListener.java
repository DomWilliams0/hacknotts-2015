package memes.game.world;

import memes.game.entity.PlayerEntity;

public interface TileActionListener {

    public void onAction(Tile tile, PlayerEntity player, boolean flag);

}
