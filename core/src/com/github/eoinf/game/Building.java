package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

/**
 * A blueprint for a building
 */
public class Building implements MapObjectBlueprint {
    // The tiles that the building will occupy
    private GridPoint2[] shape;

    private String name;
    private BuildingType buildingType;
    private BuildingEffect effect;
    private int peopleRequired;

    public Building(String name, GridPoint2[] shape, BuildingType buildingType, BuildingEffect effect, int peopleRequired) {
        this.name = name;
        this.effect = effect;
        this.shape = shape;
        this.buildingType = buildingType;
        this.peopleRequired = peopleRequired;
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

    public String getDescription() {
        return effect.getDescription();
    }

    public BuildingEffect getEffect() {
        return effect;
    }

    public int getPeopleRequired() {
        return peopleRequired;
    }

    public enum BuildingType {
        COTTAGE,
        BARRACKS,
        BLACKSMITH,
        WOODSHOP,
        TREE
    }
}
