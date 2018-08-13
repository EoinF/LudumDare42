package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Unit;

public class UnitActor extends Group {
    private Image core;
    private Image clothes;
    public UnitActor(TextureManager textureManager, Unit unit, Color playerColour) {
        super();
        setTransform(false);
        setUserObject(unit);
        core = new Image(textureManager.units.basicCore);
        clothes = new Image(textureManager.units.basicClothes);
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
    public void addAction(Action action) {
        core.addAction(action);
        clothes.addAction(action);
        super.addAction(action);
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
