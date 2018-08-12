package com.github.eoinf.screens.main.controllers;

import com.github.eoinf.game.Building;
import com.github.eoinf.game.PlacedBuilding;
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
        this.placeBuildingObservers = new ArrayList<>();
        this.stateObservers = new ArrayList<>();
        this.playerObservers = new ArrayList<>();
        this.endTurnObservers = new ArrayList<>();
        this.changeBuildingObservers = new ArrayList<>();
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
    // Place new building
    //
    private List<Consumer<PlacedBuilding>> placeBuildingObservers;

    public void subscribeOnPlaceBuilding(Consumer<PlacedBuilding> onPlaceBuilding) {
        this.placeBuildingObservers.add(onPlaceBuilding);
    }

    public boolean placeBuilding(Building building, MapTile originTile, int owner) {
        int tileX = originTile.getX();
        int tileY = originTile.getY();
        Player player = null;
        for (Player p : this.players) {
            if (p.getId() == owner) {
                player = p;
                break;
            }
        }
        if (gameMap.canConstructBuilding(building, tileX, tileY, owner)
                && player.canConstructBuilding(building)) {
            System.out.println("Placing building: " + building.getName() + " at " + tileX + ", " + tileY
                    + " for player " + owner);
            List<MapTile> buildingTiles = gameMap.getBuildingTiles(building, tileX, tileY);
            for (Consumer<PlacedBuilding> observer : placeBuildingObservers) {
                observer.accept(new PlacedBuilding(building, originTile, buildingTiles, owner, false));
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


    //
    // Change Player (i.e. resource counts)
    //
    private List<Consumer<Integer>> endTurnObservers;

    public void subscribeOnEndTurn(Consumer<Integer> onEndTurn) {
        this.endTurnObservers.add(onEndTurn);
    }


    public void endPlayerTurn(int playerId) {
        System.out.println("Ending turn for player " + playerId);
        for (Consumer<Integer> observer : endTurnObservers) {
            observer.accept(playerId);
        }
    }
    //
    // Change building
    //
    private List<Consumer<PlacedBuilding>> changeBuildingObservers;
    public void subscribeOnChangeBuilding(Consumer<PlacedBuilding> onChangeBuilding) {
        this.changeBuildingObservers.add(onChangeBuilding);
    }
    public void changeBuilding(PlacedBuilding building) {
        for (Consumer<PlacedBuilding> observer : changeBuildingObservers) {
            observer.accept(building);
        }
    }
}
