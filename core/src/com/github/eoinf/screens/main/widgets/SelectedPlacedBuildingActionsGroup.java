package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class SelectedPlacedBuildingActionsGroup extends Group {

    public SelectedPlacedBuildingActionsGroup(PlacedBuilding placedBuilding, TextureManager textureManager,
                                              GameScreenController gameScreenController, GameMap gameMap) {
        setTransform(false);
        setTouchable(Touchable.childrenOnly);
        Group actionsGroup = new Group();
        actionsGroup.setTouchable(Touchable.disabled);
        actionsGroup.setPosition(placedBuilding.getOriginTile().getX() * gameMap.getTileWidth(),
                placedBuilding.getOriginTile().getY() * gameMap.getTileHeight() - 32);
        if (placedBuilding.isConstructed()) {
            ActionButton demolishButton = new ActionButton("Demolish", textureManager.skin) {
                @Override
                public void onClick() {
                    gameScreenController.destroyPlacedObject(placedBuilding);
                    actionsGroup.clearChildren();
                }
            };
            actionsGroup.addActor(demolishButton);
        }
        addActor(actionsGroup);
    }
}
