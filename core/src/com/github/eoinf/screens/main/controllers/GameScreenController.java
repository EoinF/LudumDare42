package com.github.eoinf.screens.main.controllers;

import com.github.eoinf.game.Building;
import com.github.eoinf.game.MapTile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameScreenController {

    public GameScreenController() {
        this.mapTileObservers = new ArrayList<>();
        this.selectedBuildingObservers = new ArrayList<>();
    }

    //
    // Change individual map tiles
    //
    private List<Consumer<MapTile>> mapTileObservers;
    public void subscribeOnChangeTile(Consumer<MapTile> onChangeMapTile) {
        this.mapTileObservers.add(onChangeMapTile);
    }

    public void changeTile(MapTile tile) {
        for (Consumer<MapTile> observer: mapTileObservers) {
            observer.accept(tile);
        }
    }
    //
    // Set selected building
    //
    private List<Consumer<Building>> selectedBuildingObservers;
    public void subscribeOnSelectBuilding(Consumer<Building> onSelectBuilding) {
        this.selectedBuildingObservers.add(onSelectBuilding);
    }

    public void setSelectedBuilding(Building building) {
        for (Consumer<Building> observer: selectedBuildingObservers) {
            observer.accept(building);
        }
    }
}
