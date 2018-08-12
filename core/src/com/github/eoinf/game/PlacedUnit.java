package com.github.eoinf.game;

import java.util.List;

public class PlacedUnit {
    private int owner;
    private Unit unit;
    private MapTile originTile;
    private List<MapTile> mapTiles;
    private boolean isDeployed;

    public PlacedUnit(Unit unit, MapTile originTile, List<MapTile> mapTiles, int owner,
                          boolean isDeployed) {
        this.isDeployed = isDeployed;
        this.unit = unit;
        this.mapTiles = mapTiles;
        this.originTile = originTile;
        this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public Unit getUnit() {
        return unit;
    }

    public MapTile getOriginTile() {
        return originTile;
    }

    public boolean isDeployed() {
        return isDeployed;
    }
    public void setIsDeployed(boolean isDeployed) {
        this.isDeployed = isDeployed;
    }
}
