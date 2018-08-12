package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.ConstructedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.MapTileActor;
import com.github.eoinf.screens.main.widgets.SelectedBuildingActor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MainView extends BaseView {
    private Map<MapTile, MapTileActor> mapTileActors;
    private Player[] players;
    private GameMap gameMap;

    private Group tileGroup;
    private Group buildingGroup;
    private SelectedBuildingActor selectedBuildingActor;

    public MainView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager, GameScreenController gameScreenController,
                    int playerId) {
        super(startX, startY, width, height, batch, textureManager);
        this.initSubscriptions(gameScreenController);
        this.textureManager = textureManager;
        mapTileActors = new HashMap<>();

        selectedBuildingActor = new SelectedBuildingActor(textureManager, playerId);

        tileGroup = new Group();
        buildingGroup = new Group();

        rootTable.addActor(tileGroup);
        rootTable.addActor(buildingGroup);
        rootTable.addActor(selectedBuildingActor);

        stage.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (selectedBuildingActor.isValidConstructionSite()) {
                    System.out.println("valid construction");
                    int tileX = (int)(selectedBuildingActor.getX() / gameMap.getTileWidth());
                    int tileY = (int) (selectedBuildingActor.getY() / gameMap.getTileHeight());
                    gameScreenController.constructBuilding((Building) selectedBuildingActor.getUserObject(),
                            gameMap.getTile(tileX, tileY), playerId);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });
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
        gameScreenController.subscribeOnConstructBuilding(new Consumer<ConstructedBuilding>() {
            @Override
            public void accept(ConstructedBuilding constructedBuilding) {
                selectedBuildingActor.addNewConstructedBuilding(constructedBuilding);
                addNewBuilding(constructedBuilding);
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

    public void setBuildings(List<ConstructedBuilding> gameBuildings) {
        selectedBuildingActor.setConstructedBuildings(gameBuildings);
        for(ConstructedBuilding constructedBuilding: gameBuildings) {
            addNewBuilding(constructedBuilding);
        }
    }

    private void updateTile(MapTile tile) {
        MapTileActor actor = mapTileActors.get(tile);
        actor.setTile(textureManager, tile, players);
    }

    private void addNewBuilding(ConstructedBuilding constructedBuilding) {
        Image buildingActor = new Image(textureManager.buildings.getByType(constructedBuilding.getBuilding().getType()));
        buildingActor.setPosition(constructedBuilding.getOriginTile().getX() * gameMap.getTileWidth(),
                constructedBuilding.getOriginTile().getY() * gameMap.getTileHeight());
        buildingGroup.addActor(buildingActor);
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
