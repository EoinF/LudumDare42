package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.PlacedUnit;

public class PlacedUnitActor extends Group {
    private static final Color PLACED_COLOUR = new Color(0.4f, 0.2f, 0.5f, 0.8f);

    public PlacedUnitActor(TextureManager textureManager, PlacedUnit placedUnit, Color playerColour) {
        setTransform(false); // Avoids triggering a flush of spritebatch on drawing this

        UnitActor unitActor = new UnitActor(textureManager, placedUnit.getUnit(), playerColour);

        if (!placedUnit.isDeployed()) {
            Image background = new Image(textureManager.tiles.blank);
            background.setColor(PLACED_COLOUR);
            addActor(background);
        }
        addActor(unitActor);
    }
}
