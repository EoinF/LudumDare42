package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

/**
 * A blueprint for a building
 */
public class Building {
    // The tiles that the building will occupy
    private GridPoint2[] shape;

    private String name;
    private BuildingType buildingType;

    public Building(String name, GridPoint2[] shape, BuildingType buildingType) {
        this.name = name;
        this.shape = shape;
        this.buildingType = buildingType;
    }

    public BuildingType getType() {
        return buildingType;
    }

    public GridPoint2[] getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public enum BuildingType {
        COTTAGE,
        BARRACKS
    }
}
