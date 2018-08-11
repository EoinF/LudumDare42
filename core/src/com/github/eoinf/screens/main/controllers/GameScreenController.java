package com.github.eoinf.screens.main.controllers;

import com.github.eoinf.game.MapTile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameScreenController {
    List<Consumer<MapTile>> mapTileObservers;

    public GameScreenController() {
        this.mapTileObservers = new ArrayList<>();
    }

    public void subscribeOnChangeTile(Consumer<MapTile> onChangeMapTile) {
        this.mapTileObservers.add(onChangeMapTile);
    }

    public void changeTile(MapTile tile) {
        for (Consumer<MapTile> observer: mapTileObservers) {
            observer.accept(tile);
        }
    }
}
