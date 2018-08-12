package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

public class ConstructedBuilding {
    private Building building;
    private MapTile originTile;
    private List<MapTile> mapTiles;
    private int owner;

    public ConstructedBuilding(Building building, MapTile originTile, List<MapTile> mapTiles, int owner) {
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
}
