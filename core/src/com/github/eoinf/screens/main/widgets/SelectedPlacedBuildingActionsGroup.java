package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class SelectedPlacedBuildingActionsGroup extends Group {

    public SelectedPlacedBuildingActionsGroup(PlacedBuilding placedBuilding, TextureManager textureManager,
                                              GameScreenController gameScreenController) {
        setTransform(false);
        if (placedBuilding.isConstructed()) {
            TextButton demolishButton = new TextButton("Demolish", textureManager.skin);
            demolishButton.addListener(new ClickListener(Input.Buttons.LEFT) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameScreenController.destroyPlacedObject(placedBuilding);
                    super.clicked(event, x, y);
                }
            });
            addActor(demolishButton);
        }
    }
}
