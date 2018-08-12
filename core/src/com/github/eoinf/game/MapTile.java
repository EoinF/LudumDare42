package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

public class MapTile {
    private int x;
    private int y;

    // The id of the player owning this tile
    private int ownerId;
    public static final int NO_OWNER = -1;

    private Building building;
    private GridPoint2 buildingOffset;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MapTile(int x, int y, int ownerId) {
        this.x = x;
        this.y = y;
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }
    public boolean isOwnedBy(int id) {
        return id == ownerId;
    }

    public void setBuildingPart(Building building, int offsetX, int offsetY) {
        this.building = building;
        this.buildingOffset = new GridPoint2(offsetX, offsetY);
    }

    public Building getBuilding() {
        return building;
    }

    public GridPoint2 getBuildingOffset() {
        return buildingOffset;
    }
}
