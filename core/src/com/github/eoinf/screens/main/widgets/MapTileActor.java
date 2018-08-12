package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;

public class MapTileActor extends Group {

    public void setTile(TextureManager textureManager, MapTile tile, Player[] players) {
        clear();
        setTransform(false);
        addBackground(textureManager);
        addBorder(textureManager, tile, players);
    }

    private void addBackground(TextureManager textureManager) {
        Image background = new Image(textureManager.tiles.grass);
        addActor(background);
    }

    private void addBorder(TextureManager textureManager, MapTile tile, Player[] players) {
        Image border = new Image(textureManager.tiles.border);

        Color borderColour = new Color(0.1f, 0.1f, 0.1f, 1);
        if (tile.getOwnerId() != MapTile.NO_OWNER) {
            for (Player player : players) {
                if (player.getId() == tile.getOwnerId()) {
                    borderColour = player.getColour();
                    break;
                }
            }
        }
        borderColour = borderColour.cpy();
        borderColour.a = 0.5f;
        border.setColor(borderColour);
        addActor(border);
    }
}
