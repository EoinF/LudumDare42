package com.github.eoinf.game;

public class PlacedUnit implements PlacedObject{
    private int owner;
    private Unit unit;
    private MapTile originTile;
    private boolean isDeployed;
    private boolean isAlive;

    private MapTile destinationTile;
    private MapTile targetTile;

    public PlacedUnit(Unit unit, MapTile originTile, int owner, boolean isDeployed) {
        this.isDeployed = isDeployed;
        this.isAlive = true;
        this.unit = unit;
        this.originTile = originTile;
        this.destinationTile = null;
        this.targetTile = null;
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

    @Override
    public MapObjectBlueprint getBlueprint() {
        return unit;
    }

    public void setDestinationTile(MapTile tile) {
        destinationTile = tile;
    }

    public void setMapLocation(MapTile tile) {
        this.originTile = tile;
    }

    public MapTile getDestinationTile() {
        return destinationTile;
    }

    public MapTile getTarget() {
        return targetTile;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
