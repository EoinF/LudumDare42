package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;

import java.util.Map;

public class BuildingsTable extends Table {
    private Table housingTable;
    private Table militaryTable;
    private TextureManager textureManager;

    public BuildingsTable(TextureManager textureManager) {
        super();
        this.textureManager = textureManager;

        Label label = new Label("Housing", textureManager.skin);
        label.setColor(Color.BLACK);
        add(label);
        row();

        housingTable = new Table();
        add(housingTable).left();
        row();

        label = new Label("Military", textureManager.skin);
        label.setColor(Color.BLACK);
        add(label);
        row();

        militaryTable = new Table();
        add(militaryTable).left();
        row();
    }

    public void setBuildings(Map<BuildingCategory, Building[]> buildingCategoryMap, int tileWidth, int tileHeight) {
        //
        // Housing
        //
        for (Building building: buildingCategoryMap.get(BuildingCategory.HOUSING)) {
            addBuildingRow(tileWidth, tileHeight, building, housingTable);
        }

        //
        // Military
        //
        for (Building building: buildingCategoryMap.get(BuildingCategory.MILITARY)) {
            addBuildingRow(tileWidth, tileHeight, building, militaryTable);
        }
    }

    private void addBuildingRow(int tileWidth, int tileHeight, Building building, Table categoryTable) {
        categoryTable.add(new Label(building.getName(), textureManager.skin)).top();

        BuildingActor buildingWidget = new BuildingActor(this.textureManager, building, tileWidth, tileHeight);

        categoryTable.add(buildingWidget).fillX().expandX().left();
        row();
    }


}
