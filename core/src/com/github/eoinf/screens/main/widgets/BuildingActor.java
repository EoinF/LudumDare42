package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;

public class BuildingActor extends Table {
    public BuildingActor(TextureManager textureManager, Building building) {
        super();
        setUserObject(building);
        add(new Image(textureManager.buildings.getByType(building.getType())));
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
