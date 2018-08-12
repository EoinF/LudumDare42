package com.github.eoinf.game;

public interface PlacedObject {
    MapObjectBlueprint getBlueprint();
    MapTile getOriginTile();
    int getOwner();
}
