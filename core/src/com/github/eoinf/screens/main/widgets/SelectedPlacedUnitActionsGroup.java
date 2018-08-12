package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class SelectedPlacedUnitActionsGroup extends Group {

    public SelectedPlacedUnitActionsGroup (PlacedUnit placedUnit, TextureManager textureManager,
                                           GameScreenController gameScreenController, GameMap gameMap) {
        setTransform(false);
        Group actionsGroup = new Group();
        actionsGroup.setPosition(placedUnit.getOriginTile().getX() * gameMap.getTileWidth(),
                placedUnit.getOriginTile().getY() * gameMap.getTileHeight() - 32);

        addActor(actionsGroup);

        if (placedUnit.isDeployed()) {
            ActionButton moveButton = new ActionButton("Move", textureManager.skin);
            moveButton.addListener(new ClickListener(Input.Buttons.LEFT) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    MovementTileGroup movementTileGroup = new MovementTileGroup(placedUnit, textureManager, gameMap,
                            gameScreenController);
                    addActor(movementTileGroup);
                    actionsGroup.clearChildren();
                }
            });
            actionsGroup.addActor(moveButton);
        }
    }
}
