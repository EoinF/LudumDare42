package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.MapTileActor;
import com.github.eoinf.screens.main.widgets.PlacedBuildingActor;
import com.github.eoinf.screens.main.widgets.SelectedBuildingActor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MainView extends BaseView {
    private Map<MapTile, MapTileActor> mapTileActors;
    private Map<PlacedBuilding, PlacedBuildingActor> placedBuildingActors;
    private Player[] players;
    private GameMap gameMap;

    private Group tileGroup;
    private Group buildingGroup;
    private SelectedBuildingActor selectedBuildingActor;

    public MainView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager, GameScreenController gameScreenController,
                    int humanPlayerId) {
        super(startX, startY, width, height, batch, textureManager);
        this.initSubscriptions(gameScreenController);
        this.textureManager = textureManager;
        mapTileActors = new HashMap<>();
        placedBuildingActors = new HashMap<>();

        selectedBuildingActor = new SelectedBuildingActor(textureManager, humanPlayerId);

        tileGroup = new Group();
        buildingGroup = new Group();

        rootTable.addActor(tileGroup);
        rootTable.addActor(buildingGroup);
        rootTable.addActor(selectedBuildingActor);

        // Left clicks
        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedBuildingActor.getUserObject() != null) {
                    if (selectedBuildingActor.isValidConstructionSite()) {
                        System.out.println("valid construction");
                        int tileX = (int) (selectedBuildingActor.getX() / gameMap.getTileWidth());
                        int tileY = (int) (selectedBuildingActor.getY() / gameMap.getTileHeight());
                        gameScreenController.placeBuilding((Building) selectedBuildingActor.getUserObject(),
                                gameMap.getTile(tileX, tileY), humanPlayerId);
                    }
                }
                super.clicked(event, x, y);
            }
        });

        // Right clicks
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
                              @Override
                              public void clicked(InputEvent event, float x, float y) {
                                  gameScreenController.setSelectedBuilding(null);
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
        gameScreenController.subscribeOnSelectBuilding(new Consumer<Building>() {
            @Override
            public void accept(Building building) {
                selectedBuildingActor.setBuilding(building);
            }
        });
        gameScreenController.subscribeOnPlaceBuilding(new Consumer<PlacedBuilding>() {
            @Override
            public void accept(PlacedBuilding placedBuilding) {
                selectedBuildingActor.addNewPlacedBuilding(placedBuilding);
                addOrUpdateBuilding(placedBuilding);
            }
        });
        gameScreenController.subscribeOnChangeBuilding(new Consumer<PlacedBuilding>() {
            @Override
            public void accept(PlacedBuilding placedBuilding) {
                addOrUpdateBuilding(placedBuilding);
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
        selectedBuildingActor.setGameMap(gameMap);
    }

    public void setBuildings(List<PlacedBuilding> gameBuildings) {
        selectedBuildingActor.setConstructedBuildings(gameBuildings);
        for(PlacedBuilding constructedBuilding: gameBuildings) {
            addOrUpdateBuilding(constructedBuilding);
        }
    }

    private void updateTile(MapTile tile) {
        MapTileActor actor = mapTileActors.get(tile);
        actor.setTile(textureManager, tile, players);
    }

    private void addOrUpdateBuilding(PlacedBuilding placedBuilding) {
        Actor existingActor = placedBuildingActors.get(placedBuilding);
        if (existingActor != null) {
            existingActor.remove();
        }
        PlacedBuildingActor buildingActor = new PlacedBuildingActor(textureManager, placedBuilding);
        buildingActor.setPosition(placedBuilding.getOriginTile().getX() * gameMap.getTileWidth(),
                placedBuilding.getOriginTile().getY() * gameMap.getTileHeight());
        buildingGroup.addActor(buildingActor);
        placedBuildingActors.put(placedBuilding, buildingActor);
    }

    @Override
    public void update(float delta) {
        int mouseX = Gdx.input.getX() - this.screenX;
        int mouseY = (int) camera.viewportHeight - Gdx.input.getY();

        int tileX = (mouseX / gameMap.getTileWidth());
        int tileY = (mouseY / gameMap.getTileHeight());
        selectedBuildingActor.setTileXY(tileX, tileY);

        super.update(delta);
    }
}
