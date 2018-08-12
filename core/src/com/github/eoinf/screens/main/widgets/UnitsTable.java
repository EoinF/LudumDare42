package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Unit;

public class UnitsTable extends Table {
    TextureManager textureManager;

    public UnitsTable(TextureManager textureManager) {
        super();
        this.textureManager = textureManager;
    }


    public void setUnits(Unit[] unitTypes, int tileWidth, int tileHeight, Color playerColour) {
        for (Unit unit : unitTypes) {
            add(new Label(unit.getName(), textureManager.skin)).top();

            UnitActor unitWidget = new UnitActor(this.textureManager, unit, playerColour);

            for (GridPoint2 tileOccupied : unit.getShape()) {
                Image tileImage = new Image(textureManager.tiles.border);
                tileImage.setPosition(tileOccupied.x * tileWidth, tileOccupied.y * tileHeight);
                tileImage.setColor(1, 1, 1, 0.5f);
                unitWidget.addActor(tileImage);
            }
            add(unitWidget).fillX().expandX().left();
            row();
        }
    }
}

