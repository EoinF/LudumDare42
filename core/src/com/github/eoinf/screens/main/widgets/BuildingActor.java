package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;

public class BuildingActor extends Group {
    public BuildingActor(TextureManager textureManager, Building building, int tileWidth, int tileHeight) {
        super();
        setTransform(false);
        setUserObject(building);
        Image buildingImage = new Image(textureManager.buildings.getByType(building.getType()));
        addActor(buildingImage);

        setHeight(buildingImage.getHeight());

        for (GridPoint2 tileOccupied : building.getShape()) {
            Image tileImage = new Image(textureManager.tiles.border);
            tileImage.setPosition(tileOccupied.x * tileWidth, tileOccupied.y * tileHeight);
            tileImage.setColor(1, 1, 1, 0.5f);
            addActor(tileImage);
        }

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
