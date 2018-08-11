package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;

public class SelectedBuildingActor extends Group {
    private Group tilesGroup;
    private float timer = 0;

    public SelectedBuildingActor(TextureManager textureManager, Building building, int tileWidth, int tileHeight) {
        addActor(new Image(textureManager.buildings.getByType(building.getType())));

        tilesGroup = new Group();
        for (GridPoint2 tileOccupied : building.getShape()) {
            Image tileImage = new Image(textureManager.tiles.blank);
            tileImage.setPosition(tileOccupied.x * tileWidth, tileOccupied.y * tileHeight);
            tileImage.setColor(1, 1, 1, 0.5f);
            tilesGroup.addActor(tileImage);
        }
        addActor(tilesGroup);
    }

    @Override
    public void act(float delta) {
        timer += delta;
        float r = 0.4f + 0.3f * (float)Math.sin(4 * timer);
        float g = 0.6f + 0.3f * (float)Math.sin(4 * timer);
        float b = 0.6f;
        for (Actor child: this.tilesGroup.getChildren()) {

            child.setColor(r, g, b, 0.3f);
        }
        super.act(delta);
    }
}
