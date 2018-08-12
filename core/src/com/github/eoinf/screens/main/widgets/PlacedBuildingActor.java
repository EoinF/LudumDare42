package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.PlacedBuilding;

public class PlacedBuildingActor extends Group implements PlacedObjectActor {
    private static final Color PLACED_COLOUR = new Color(0.6f, 0.2f, 1f, 1f);

    public PlacedBuildingActor(TextureManager textureManager, PlacedBuilding placedBuilding) {
        setUserObject(placedBuilding);
        setTransform(false); // Avoids triggering a flush of spritebatch on drawing this
        Image buildingImage = new Image(textureManager.buildings.getByType(placedBuilding.getBuilding().getType()));
        if (!placedBuilding.isConstructed()) {
            buildingImage.setColor(PLACED_COLOUR);
        } else {
            buildingImage.setColor(Color.WHITE);
        }
        addActor(buildingImage);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (super.hit(x, y, touchable) != null) {
            return this;
        } else {
            return null;
        }
    }
}
