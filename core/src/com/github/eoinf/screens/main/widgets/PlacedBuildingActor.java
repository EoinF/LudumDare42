package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.PlacedBuilding;

public class PlacedBuildingActor extends Group {
    private static final Color PLACED_COLOUR = new Color(0.6f, 0.2f, 1f, 1f);

    public PlacedBuildingActor(TextureManager textureManager, PlacedBuilding placedBuilding) {
        Image buildingImage = new Image(textureManager.buildings.getByType(placedBuilding.getBuilding().getType()));
        if (!placedBuilding.isConstructed()) {
            buildingImage.setColor(PLACED_COLOUR);
        } else {
            buildingImage.setColor(Color.WHITE);
        }
        addActor(buildingImage);
    }
}
