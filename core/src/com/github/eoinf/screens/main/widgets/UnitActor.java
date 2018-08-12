package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Unit;

public class UnitActor extends Group {
    public UnitActor(TextureManager textureManager, Unit unit, Color playerColour) {
        super();
        setTransform(false);
        setUserObject(unit);
        Image core = new Image(textureManager.units.basicCore);
        Image clothes = new Image(textureManager.units.basicClothes);
        clothes.setColor(playerColour);

        addActor(core);
        addActor(clothes);
        setHeight(core.getHeight());

        switch (unit.getWeapon()) {
            case BOW_AND_ARROW:
                addActor(new Image(textureManager.units.weaponBow));
                break;
            case SWORD:
                addActor(new Image(textureManager.units.weaponSword));
                break;
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
