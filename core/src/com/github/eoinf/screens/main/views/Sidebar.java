package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.BuildingActor;
import com.github.eoinf.screens.main.widgets.BuildingCategory;
import com.github.eoinf.screens.main.widgets.BuildingsTable;

import java.util.Map;

public class Sidebar extends BaseView {

    private BuildingsTable buildingsTable;

    public Sidebar(int startX, int startY, int width, int height,
                   Batch batch, TextureManager textureManager, GameScreenController gameScreenController,
                   float viewportWidth, float viewportHeight) {
        super(startX, startY, width, height, batch, textureManager, viewportWidth, viewportHeight, Color.FIREBRICK);

        buildingsTable = new BuildingsTable(textureManager);
        buildingsTable.top().left().setFillParent(true);
        rootTable.addActor(buildingsTable);
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor hit = stage.hit(x, y, true);

                if (hit instanceof BuildingActor) {
                    gameScreenController.setSelectedBuilding((Building)hit.getUserObject());
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void setBuildings(Map<BuildingCategory, Building[]> buildingCategoryMap, int tileWidth, int tileHeight) {
        buildingsTable.setBuildings(buildingCategoryMap, tileWidth, tileHeight);
    }
}
