package com.github.eoinf.screens.main.controllers;

import com.badlogic.gdx.math.GridPoint2;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.ConstructedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.StateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameScreenController {
    private GameMap gameMap;
    private Player[] players;

    public GameScreenController(GameMap gameMap, Player[] players) {
        this.gameMap = gameMap;
        this.players = players;
        this.mapTileObservers = new ArrayList<>();
        this.selectedBuildingObservers = new ArrayList<>();
        this.constructBuildingObservers = new ArrayList<>();
        this.stateObservers = new ArrayList<>();
        this.playerObservers = new ArrayList<>();
    }

    //
    // Change individual map tiles
    //
    private List<Consumer<MapTile>> mapTileObservers;

    public void subscribeOnChangeTile(Consumer<MapTile> onChangeMapTile) {
        this.mapTileObservers.add(onChangeMapTile);
    }

    public void changeTile(MapTile tile) {
        for (Consumer<MapTile> observer : mapTileObservers) {
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
        for (Consumer<Building> observer : selectedBuildingObservers) {
            observer.accept(building);
        }
    }

    //
    // Construct building
    //
    private List<Consumer<ConstructedBuilding>> constructBuildingObservers;

    public void subscribeOnConstructBuilding(Consumer<ConstructedBuilding> onConstructBuilding) {
        this.constructBuildingObservers.add(onConstructBuilding);
    }

    public boolean constructBuilding(Building building, MapTile originTile, int owner) {
        int tileX = originTile.getX();
        int tileY = originTile.getY();
        Player player = null;
        for (Player p : this.players) {
            if (p.getId() == owner) {
                player = p;
            }
        }
        if (gameMap.canConstructBuilding(building, tileX, tileY, owner)
                && player.canConstructBuilding(building)) {
            System.out.println("Constructing building: " + building.getName() + " at " + tileX + ", " + tileY
                    + " for player " + owner);
            List<MapTile> buildingTiles = gameMap.getBuildingTiles(building, tileX, tileY);
            for (Consumer<ConstructedBuilding> observer : constructBuildingObservers) {
                observer.accept(new ConstructedBuilding(building, originTile, buildingTiles, owner));
            }
            return true;
        }
        return false;
    }

    //
    // Set State
    //
    private List<Consumer<StateManager.State>> stateObservers;

    public void subscribeOnSetState(Consumer<StateManager.State> onSetState) {
        this.stateObservers.add(onSetState);
    }

    public void setState(StateManager.State nextState) {
        for (Consumer<StateManager.State> observer : stateObservers) {
            observer.accept(nextState);
        }
    }


    //
    // Change Player (i.e. resource counts)
    //
    private List<Consumer<Player>> playerObservers;

    public void subscribeOnChangePlayer(Consumer<Player> onChangePlayer) {
        this.playerObservers.add(onChangePlayer);
    }

    public void changePlayer(Player player) {
        for (Consumer<Player> observer : playerObservers) {
            observer.accept(player);
        }
    }
}
