package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class MovementTileGroup extends Group {
    public MovementTileGroup(PlacedUnit placedUnit, TextureManager textureManager, GameMap gameMap,
                             GameScreenController gameScreenController) {
        MapTile originTile = placedUnit.getOriginTile();

        int tileX = originTile.getX();
        int tileY = originTile.getY();

        addTileAt(placedUnit, gameMap, gameScreenController, textureManager, tileX, tileY + 1);
        addTileAt(placedUnit, gameMap, gameScreenController, textureManager, tileX + 1, tileY);
        addTileAt(placedUnit, gameMap, gameScreenController, textureManager, tileX, tileY - 1);
        addTileAt(placedUnit, gameMap, gameScreenController, textureManager, tileX - 1, tileY);
    }

    void addTileAt(PlacedUnit placedUnit, GameMap gameMap, GameScreenController gameScreenController,
                   TextureManager textureManager,
                   int tileX, int tileY) {
        MapTile destinationTile = gameMap.getTile(tileX, tileY);
        if (destinationTile != null) {
            MoveTileButton moveButton = new MoveTileButton(textureManager);
            moveButton.setPosition(tileX * gameMap.getTileWidth(),
                    tileY * gameMap.getTileHeight());
            addActor(moveButton);
            moveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    placedUnit.setDestinationTile(destinationTile);
                    gameScreenController.changeUnit(placedUnit);
                    gameScreenController.setSelectedPlacedObject(null);
                }
            });
        }
    }
}
