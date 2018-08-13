package com.github.eoinf.screens.main.controllers;

import com.github.eoinf.game.Building;
import com.github.eoinf.game.MapObjectBlueprint;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.PlacedObject;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.StateManager;
import com.github.eoinf.game.Unit;

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
        this.selectedObjectObservers = new ArrayList<>();
        this.placeBuildingObservers = new ArrayList<>();
        this.stateObservers = new ArrayList<>();
        this.playerObservers = new ArrayList<>();
        this.endTurnObservers = new ArrayList<>();
        this.changeBuildingObservers = new ArrayList<>();

        this.placeUnitObservers = new ArrayList<>();
        this.changeUnitObservers = new ArrayList<>();

        this.selectPlacedObjectObservers = new ArrayList<>();
        this.destroyPlacedObjectObservers = new ArrayList<>();
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
    private List<Consumer<MapObjectBlueprint>> selectedObjectObservers;

    public void subscribeOnSelectObject(Consumer<MapObjectBlueprint> onSelectObject) {
        this.selectedObjectObservers.add(onSelectObject);
    }

    public void setSelectedObject(MapObjectBlueprint blueprint) {
        for (Consumer<MapObjectBlueprint> observer : selectedObjectObservers) {
            observer.accept(blueprint);
        }
    }

    //
    // Place new building
    //
    private List<Consumer<PlacedBuilding>> placeBuildingObservers;

    public void subscribeOnPlaceBuilding(Consumer<PlacedBuilding> onPlaceBuilding) {
        this.placeBuildingObservers.add(onPlaceBuilding);
    }

    public boolean placeObject(MapObjectBlueprint blueprint, MapTile originTile, int owner) {
        if (blueprint instanceof Building) {
            return placeBuilding((Building) blueprint, originTile, owner);
        } else if (blueprint instanceof Unit) {
            return placeUnit((Unit) blueprint, originTile, owner);
        } else {
            return false;
        }
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
        boolean isConstructed = false;
        if (owner == - 1) {
            isConstructed = true;
        }

        if (owner == -1
                || (gameMap.canConstructBuilding(building, tileX, tileY, owner)
                    && player.canConstructBuilding(building))) {
            System.out.println("Placing building: " + building.getName() + " at " + tileX + ", " + tileY
                    + " for player " + owner);
            List<MapTile> buildingTiles = gameMap.getBlueprintTiles(building, tileX, tileY);
            PlacedBuilding placedBuilding = new PlacedBuilding(building, originTile, buildingTiles, owner, isConstructed);
            for (Consumer<PlacedBuilding> observer : placeBuildingObservers) {
                observer.accept(placedBuilding);
            }
            return true;
        }
        return false;
    }


    //
    // Place new unit
    //
    private List<Consumer<PlacedUnit>> placeUnitObservers;

    public void subscribeOnPlaceUnit(Consumer<PlacedUnit> onPlaceUnit) {
        this.placeUnitObservers.add(onPlaceUnit);
    }

    public boolean placeUnit(Unit unit, MapTile originTile, int owner) {
        int tileX = originTile.getX();
        int tileY = originTile.getY();
        Player player = null;
        for (Player p : this.players) {
            if (p.getId() == owner) {
                player = p;
                break;
            }
        }
        if (gameMap.canPlaceUnit(unit, tileX, tileY, owner)
                && player.canCreateUnit(unit)) {
            System.out.println("Placing unit: " + unit.getName() + " at " + tileX + ", " + tileY
                    + " for player " + owner);
            PlacedUnit placedUnit = new PlacedUnit(unit, originTile, owner, false);
            for (Consumer<PlacedUnit> observer : placeUnitObservers) {
                observer.accept(placedUnit);
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
    //
    // Change building
    //
    private List<Consumer<PlacedUnit>> changeUnitObservers;
    public void subscribeOnChangeUnit(Consumer<PlacedUnit> onChangeUnit) {
        this.changeUnitObservers.add(onChangeUnit);
    }
    public void changeUnit(PlacedUnit unit) {
        for (Consumer<PlacedUnit> observer : changeUnitObservers) {
            observer.accept(unit);
        }
    }

    //
    // Destroy placed object
    //
    private List<Consumer<PlacedObject>> destroyPlacedObjectObservers;
    public void subscribeOnDestroyPlacedObject(Consumer<PlacedObject> onDestroyPlacedObject) {
        this.destroyPlacedObjectObservers.add(onDestroyPlacedObject);
    }
    public void destroyPlacedObject(PlacedObject object) {
        for (Consumer<PlacedObject> observer : destroyPlacedObjectObservers) {
            observer.accept(object);
        }
    }


    //
    // Select placed object
    //
    private List<Consumer<PlacedObject>> selectPlacedObjectObservers;
    public void subscribeOnSelectPlacedObject(Consumer<PlacedObject> onSelectPlacedObject) {
        this.selectPlacedObjectObservers.add(onSelectPlacedObject);
    }
    public void setSelectedPlacedObject(PlacedObject object) {
        for (Consumer<PlacedObject> observer : selectPlacedObjectObservers) {
            observer.accept(object);
        }
    }
}
