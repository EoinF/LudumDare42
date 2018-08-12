package com.github.eoinf.game;

import java.util.List;

public class PlacedBuilding {
    private Building building;
    private MapTile originTile;
    private List<MapTile> mapTiles;
    private int owner;
    private boolean isConstructed;

    public PlacedBuilding(Building building, MapTile originTile, List<MapTile> mapTiles, int owner,
                          boolean isConstructed) {
        this.isConstructed = isConstructed;
        this.building = building;
        this.mapTiles = mapTiles;
        this.originTile = originTile;
        this.owner = owner;
    }

    public Building getBuilding() {
        return building;
    }

    public MapTile getOriginTile() {
        return originTile;
    }

    public int getOwner() {
        return owner;
    }

    public boolean containsTile(MapTile mapTile) {
        return this.mapTiles.contains(mapTile);
    }

    public boolean isConstructed() {
        return isConstructed;
    }

    public void setIsConstructed(boolean isConstructed) {
        this.isConstructed = isConstructed;
    }
}
