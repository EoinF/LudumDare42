package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.BuildingCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class AIController {
    private Player player;
    private Player[] players;
    private List<PlacedBuilding> buildings;
    private List<PlacedUnit> units;
    private boolean isPlanningPhase;
    private boolean isTurnEnded;
    private GameScreenController gameScreenController;
    private Map<BuildingCategory, Building[]> buildingCategoryMap;
    private Unit[] unitTypes;
    private GameMap gameMap;
    private Map<Unit.WeaponType, ArrayList<GridPoint2>> attackGrids;

    public AIController(Player player, Player[] players, List<PlacedBuilding> buildings, List<PlacedUnit> units,
                        StateManager stateManager, GameScreenController gameScreenController,
                        Map<BuildingCategory, Building[]> buildingCategoryMap, Unit[] unitTypes, GameMap gameMap) {
        this.players = players;
        this.buildings = buildings;
        this.player = player;
        this.units = units;
        this.isPlanningPhase = stateManager.getState() == StateManager.State.PLANNING_PHASE;
        this.gameScreenController = gameScreenController;
        this.buildingCategoryMap = buildingCategoryMap;
        this.unitTypes = unitTypes;
        this.gameMap = gameMap;

        gameScreenController.subscribeOnSetState(new Consumer<StateManager.State>() {
            @Override
            public void accept(StateManager.State state) {
                isPlanningPhase = state == StateManager.State.PLANNING_PHASE;
                isTurnEnded = false;
            }
        });

        gameScreenController.subscribeOnEndTurn(new Consumer<Integer>() {
            @Override
            public void accept(Integer playerTurnEndedId) {
                if (player.getId() == playerTurnEndedId) {
                    isTurnEnded = true;
                }
            }
        });

        createAttackGrids();
    }

    private void createAttackGrids() {
        attackGrids = new HashMap<>();
        ArrayList<GridPoint2> points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i - 2 != 0 && j - 2 != 0) { // Avoid the middle square
                    points.add(new GridPoint2(i - 2, j - 2));
                }
            }
        }

        attackGrids.put(Unit.WeaponType.BOW_AND_ARROW, points);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i - 1 != 0 && j - 1 != 0) { // Avoid the middle square
                    points.add(new GridPoint2(i - 1, j - 1));
                }
            }
        }

        attackGrids.put(Unit.WeaponType.SWORD, points);
    }

    private Building getBuildingByType(Building.BuildingType type) {
        for (BuildingCategory category : buildingCategoryMap.keySet()) {
            for (Building building : buildingCategoryMap.get(category)) {
                if (building.getType() == type) {
                    return building;
                }
            }
        }
        return null;
    }

    private Unit getUnitByType(Unit.WeaponType type) {
        for (Unit unit : unitTypes) {
            if (unit.getWeapon() == type) {
                return unit;
            }
        }
        return null;
    }


    public void update() {
        if (isPlanningPhase && !isTurnEnded) {
            Random random = new Random();

            placeBuildings(random);
            placeUnits(random);
            controlUnits(random);

            this.gameScreenController.endPlayerTurn(this.player.getId());
        }
    }

    private void controlUnits(Random random) {
        for (PlacedUnit unit : units) {
            if (unit.getOwner() == player.getId()) {
                PlacedUnit enemy = findNearbyEnemy(unit);
                if (enemy != null) {
                    unit.setTarget(enemy.getOriginTile());
                } else {
                    //
                    // Try to siege
                    //
                    if (unit.getOriginTile().getBuilding() != null
                            && unit.getOriginTile().getBuilding().getOwner() != unit.getOwner()) { // Only enemy buildings
                        unit.setRazeTarget(unit.getOriginTile().getBuilding());
                    } else {
                        //
                        // Just move the unit
                        //
                        int tileX = unit.getOriginTile().getX();
                        int tileY = unit.getOriginTile().getY();

                        int directionY;
                        int directionX;
                        if (tileY > gameMap.getTileOwnedCutoff()) {
                            directionY = -1;
                            directionX = random.nextInt(3) - 1;
                        } else {
                            GridPoint2 result = getDirectionToEnemyBuilding(tileX, tileY);
                            directionX = result.x;
                            directionY = result.y;
                        }

                        // Try to move there

                        MapTile destTile = gameMap.getTile(tileX + directionX, tileY + directionY);
                        if (destTile == null) {
                            destTile = gameMap.getTile(tileX, tileY + directionY);
                            if (destTile == null) {
                                destTile = gameMap.getTile(tileX + directionX, tileY);
                            }
                        }
                        unit.setDestinationTile(destTile);
                    }
                }
            }
        }
    }

    private GridPoint2 getDirectionToEnemyBuilding(int tileX, int tileY) {
        for (PlacedBuilding building: buildings) {
            if (building.getOwner() != player.getId()) {
                int directionX = building.getOriginTile().getX() - tileX;
                int directionY = building.getOriginTile().getY() - tileY;
                if (directionX > 1) {
                    directionX = 2;
                } else if (directionX < -1) {
                    directionX = -2;
                }
                if (directionY > 1) {
                    directionY = 2;
                } else if (directionY < -1) {
                    directionY = -2;
                }

                return new GridPoint2(directionX, directionY);
            }
        }
        return new GridPoint2(0, 0);
    }

    private PlacedUnit findNearbyEnemy(PlacedUnit unit) {
        int tileX = unit.getOriginTile().getX();
        int tileY = unit.getOriginTile().getY();
        List<GridPoint2> attackGrid = attackGrids.get(unit.getUnit().getWeapon());
        if (attackGrid != null) {
            for (PlacedUnit otherUnit : units) {
                if (unit.getOwner() != otherUnit.getOwner()) {
                    int enemyTileX = otherUnit.getOriginTile().getX();
                    int enemyTileY = otherUnit.getOriginTile().getY();
                    for (GridPoint2 point : attackGrid) {
                        if (tileX + point.x == enemyTileX && tileY + point.y == enemyTileY) {
                            return otherUnit;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void placeBuildings(Random random) {
        if (player.people.total < 20) {
            tryPlaceBuilding(getBuildingByType(Building.BuildingType.COTTAGE), random);
        } else {
            tryPlaceBuilding(getBuildingByType(Building.BuildingType.BARRACKS), random);
        }
    }

    private void placeUnits(Random random) {
        int tileX = random.nextInt(gameMap.getWidth());
        int tileY = gameMap.getHeight() - random.nextInt(gameMap.getTileOwnedCutoff());
        // 20% of the time create a vandal
        if (random.nextInt(10) < 2) {
            Unit vandal = getUnitByType(Unit.WeaponType.NONE);
            if (player.canCreateUnit(vandal)) {
                int directionToTry = random.nextInt(1);
                if (directionToTry == 0) {
                    directionToTry = -1;
                }
                tryPlaceUnit(vandal, tileX, tileY, directionToTry);
            }
        }

        Unit sword = getUnitByType(Unit.WeaponType.SWORD);
        Unit bow = getUnitByType(Unit.WeaponType.BOW_AND_ARROW);
        while (player.canCreateUnit(sword) && player.canCreateUnit(bow)) {
            tileX = random.nextInt(gameMap.getWidth());
            tileY = gameMap.getHeight() - (gameMap.getTileOwnedCutoff() - 1);
            int directionToTry = random.nextInt(1);
            if (directionToTry == 0) {
                directionToTry = -1;
            }

            if (player.canCreateUnit(sword)) {
                tryPlaceUnit(sword, tileX, tileY, directionToTry);
                continue;
            }
            if (player.canCreateUnit(bow)) {
                tryPlaceUnit(bow, tileX, tileY, directionToTry);
            }
        }
    }

    private void tryPlaceBuilding(Building building, Random random) {
        int tileX, tileY;
        for (int attempt = 0; attempt < 30; attempt++) {
            tileX = random.nextInt(gameMap.getWidth());
            tileY = gameMap.getHeight() - random.nextInt(gameMap.getTileOwnedCutoff());
            MapTile tile = gameMap.getTile(tileX, tileY);
            if (tile != null && this.gameScreenController.placeBuilding(building, tile, player.getId())) {
                return;
            }
        }
    }

    private void tryPlaceUnit(Unit unit, int tileX, int tileY, int directionToTry) {
        for (int attempt = 0; attempt < 10; attempt++) {
            MapTile tile = gameMap.getTile(tileX, tileY);
            if (tile != null && this.gameScreenController.placeUnit(unit, tile, player.getId())) {
                return;
            } else {
                tileX += directionToTry;
            }
        }
    }
}
