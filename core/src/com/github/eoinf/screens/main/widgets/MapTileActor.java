package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.MapTile;

public class MapTileActor extends Group {
    public MapTileActor(TextureManager textureManager, MapTile tile) {
        Image background = new Image(textureManager.tiles.grass);
        Image border = new Image(textureManager.tiles.border);
        border.setColor(Color.BLACK);

        addActor(background);
        addActor(border);
    }
}
