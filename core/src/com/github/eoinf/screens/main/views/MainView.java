package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.MapObjectBlueprint;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.PlacedObject;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.ActionActor;
import com.github.eoinf.screens.main.widgets.MapTileActor;
import com.github.eoinf.screens.main.widgets.PlacedBuildingActor;
import com.github.eoinf.screens.main.widgets.PlacedObjectActor;
import com.github.eoinf.screens.main.widgets.PlacedUnitActor;
import com.github.eoinf.screens.main.widgets.SelectedObjectActor;
import com.github.eoinf.screens.main.widgets.SelectedPlacedObjectActor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MainView extends BaseView {
    private Map<MapTile, MapTileActor> mapTileActors;
    private Map<PlacedBuilding, PlacedBuildingActor> placedBuildingActors;
    private Map<PlacedUnit, PlacedUnitActor> placedUnitActors;
    private Player[] players;
    private Player humanPlayer;
    private GameMap gameMap;

    private Group tileGroup;
    private Group buildingGroup;
    private Group unitGroup;

    private SelectedObjectActor selectedObjectActor;
    private SelectedPlacedObjectActor selectedPlacedObjectActor;

    public MainView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager, GameScreenController gameScreenController,
                    Player humanPlayer) {
        super(startX, startY, width, height, batch, textureManager);
        this.initSubscriptions(gameScreenController);
        this.textureManager = textureManager;
        this.humanPlayer = humanPlayer;

        mapTileActors = new HashMap<>();
        placedBuildingActors = new HashMap<>();
        placedUnitActors = new HashMap<>();

        selectedObjectActor = new SelectedObjectActor(textureManager, humanPlayer);
        selectedPlacedObjectActor = new SelectedPlacedObjectActor(textureManager, humanPlayer, gameScreenController);

        tileGroup = new Group();
        buildingGroup = new Group();
        unitGroup = new Group();

        rootTable.addActor(tileGroup);
        rootTable.addActor(buildingGroup);
        rootTable.addActor(unitGroup);
        rootTable.addActor(selectedObjectActor);
        rootTable.addActor(selectedPlacedObjectActor);

        // Left clicks
        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor hit = stage.hit(x, y, false);

                System.out.println(hit);
                System.out.println(hit instanceof ActionActor);
                if (hit instanceof ActionActor) {
                    ((ActionActor) hit).onClick();
                }
                else if (hit instanceof PlacedObjectActor) {
                    gameScreenController.setSelectedPlacedObject((PlacedObject) hit.getUserObject());
                } else {
                    MapObjectBlueprint blueprint = (MapObjectBlueprint) selectedObjectActor.getUserObject();
                    if (blueprint != null) {
                        if (selectedObjectActor.isValidConstructionSite()) {
                            System.out.println("valid construction");
                            int tileX = (int) (selectedObjectActor.getX() / gameMap.getTileWidth());
                            int tileY = (int) (selectedObjectActor.getY() / gameMap.getTileHeight());
                            gameScreenController.placeObject((MapObjectBlueprint) selectedObjectActor.getUserObject(),
                                    gameMap.getTile(tileX, tileY), humanPlayer.getId());
                        }
                    } else {
                        gameScreenController.setSelectedPlacedObject(null);
                    }
                }
                //super.clicked(event, x, y);
            }
        });

        // Right clicks
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
                              @Override
                              public void clicked(InputEvent event, float x, float y) {
                                  gameScreenController.setSelectedObject(null);
                                  super.clicked(event, x, y);
                              }
                          }
        );
    }

    private void initSubscriptions(GameScreenController gameScreenController) {
        gameScreenController.subscribeOnChangeTile(new Consumer<MapTile>() {
            @Override
            public void accept(MapTile tile) {
                updateTile(tile);
            }
        });
        gameScreenController.subscribeOnSelectObject(new Consumer<MapObjectBlueprint>() {
            @Override
            public void accept(MapObjectBlueprint building) {
                selectedObjectActor.setObject(building);
            }
        });
        gameScreenController.subscribeOnSelectPlacedObject(new Consumer<PlacedObject>() {
            @Override
            public void accept(PlacedObject placedObject) {
                if (placedObject != null) {
                    selectedPlacedObjectActor.setPlacedObject(placedObject, gameMap);
                    selectedObjectActor.setObject(null);
                } else {
                    selectedPlacedObjectActor.clearPlacedObject();
                }
            }
        });

        gameScreenController.subscribeOnPlaceBuilding(new Consumer<PlacedBuilding>() {
            @Override
            public void accept(PlacedBuilding placedBuilding) {
                selectedObjectActor.addNewPlacedBuilding(placedBuilding);
                addOrUpdateBuilding(placedBuilding);
            }
        });
        gameScreenController.subscribeOnPlaceUnit(new Consumer<PlacedUnit>() {
            @Override
            public void accept(PlacedUnit placedUnit) {
                addOrUpdateUnit(placedUnit);
            }
        });
        gameScreenController.subscribeOnChangeBuilding(new Consumer<PlacedBuilding>() {
            @Override
            public void accept(PlacedBuilding placedBuilding) {
                addOrUpdateBuilding(placedBuilding);
            }
        });

        gameScreenController.subscribeOnChangeUnit(new Consumer<PlacedUnit>() {
            @Override
            public void accept(PlacedUnit placedUnit) {
                addOrUpdateUnit(placedUnit);
            }
        });

        gameScreenController.subscribeOnDestroyPlacedObject(new Consumer<PlacedObject>() {
            @Override
            public void accept(PlacedObject placedObject) {
                if (selectedPlacedObjectActor.getUserObject() == placedObject) {
                    selectedPlacedObjectActor.clearPlacedObject();
                }
                if (placedObject instanceof PlacedBuilding) {
                    selectedObjectActor.removePlacedBuilding((PlacedBuilding) placedObject);
                    removeBuilding((PlacedBuilding)placedObject);
                } else {
                    removeUnit((PlacedUnit)placedObject);
                }
            }
        });
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setMap(GameMap gameMap) {
        this.gameMap = gameMap;
        int tileWidth = gameMap.getTileWidth();
        int tileHeight = gameMap.getTileHeight();
        for (MapTile[] row : gameMap.getTiles()) {
            for (MapTile tile : row) {
                MapTileActor tileActor = new MapTileActor();
                tileActor.setTile(textureManager, tile, players);
                tileActor.setBounds(tile.getX() * tileWidth, tile.getY() * tileHeight, tileWidth, tileHeight);
                mapTileActors.put(tile, tileActor);
                tileGroup.addActor(tileActor);
            }
        }
        selectedObjectActor.setGameMap(gameMap);
    }

    public void setBuildings(List<PlacedBuilding> gameBuildings) {
        selectedObjectActor.setPlacedBuildings(gameBuildings);
        for(PlacedBuilding constructedBuilding: gameBuildings) {
            addOrUpdateBuilding(constructedBuilding);
        }
    }

    private void updateTile(MapTile tile) {
        MapTileActor actor = mapTileActors.get(tile);
        actor.setTile(textureManager, tile, players);
    }

    private void addOrUpdateBuilding(PlacedBuilding placedBuilding) {
        removeBuilding(placedBuilding);
        PlacedBuildingActor buildingActor = new PlacedBuildingActor(textureManager, placedBuilding);
        buildingActor.setPosition(placedBuilding.getOriginTile().getX() * gameMap.getTileWidth(),
                placedBuilding.getOriginTile().getY() * gameMap.getTileHeight());
        buildingGroup.addActor(buildingActor);
        placedBuildingActors.put(placedBuilding, buildingActor);
    }

    private void removeBuilding(PlacedBuilding placedBuilding) {
        Actor existingActor = placedBuildingActors.get(placedBuilding);
        if (existingActor != null) {
            existingActor.remove();
        }
    }

    private void addOrUpdateUnit(PlacedUnit placedUnit) {
        removeUnit(placedUnit);
        PlacedUnitActor unitActor = new PlacedUnitActor(textureManager, placedUnit, gameMap, humanPlayer.getColour());
        unitActor.setPosition(placedUnit.getOriginTile().getX() * gameMap.getTileWidth(),
                placedUnit.getOriginTile().getY() * gameMap.getTileHeight());
        unitGroup.addActor(unitActor);
        placedUnitActors.put(placedUnit, unitActor);
    }

    private void removeUnit(PlacedUnit placedUnit) {
        Actor existingActor = placedUnitActors.get(placedUnit);
        if (existingActor != null) {
            existingActor.remove();
        }
    }

    @Override
    public void update(float delta) {
        Vector3 mousePositionOnScreen = new Vector3(Gdx.input.getX(),
                Gdx.input.getY(),
                0);

        Vector3 mousePositionInWorld = camera.unproject(mousePositionOnScreen, screenX, screenY,
                camera.viewportWidth, camera.viewportHeight);
        int mouseX = (int)mousePositionInWorld.x;
        int mouseY = (int)mousePositionInWorld.y;

        checkCameraInput(delta);

        int tileX = (mouseX / gameMap.getTileWidth());
        int tileY = (mouseY / gameMap.getTileHeight());
        selectedObjectActor.setTileXY(tileX, tileY);

        super.update(delta);
    }

    private void checkCameraInput(float delta) {
        final float CAMERA_SPEED = 200;
        final float CAMERA_MARGIN = 300;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += delta * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= delta * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= delta * CAMERA_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += delta * CAMERA_SPEED;
        }

        if (camera.position.x < (camera.viewportWidth / 2) - CAMERA_MARGIN) {
            camera.position.x = (camera.viewportWidth / 2) - CAMERA_MARGIN;
        } else if (camera.position.x > (camera.viewportWidth / 2) + CAMERA_MARGIN) {
            camera.position.x = (camera.viewportWidth / 2) + CAMERA_MARGIN;
        }
        if (camera.position.y < (camera.viewportHeight / 2) - CAMERA_MARGIN) {
            camera.position.y = (camera.viewportHeight / 2) - CAMERA_MARGIN;
        } else if (camera.position.y > (camera.viewportHeight / 2) + CAMERA_MARGIN) {
            camera.position.y = (camera.viewportHeight / 2) + CAMERA_MARGIN;
        }
    }
}
