package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.MapTileActor;
import com.github.eoinf.screens.main.widgets.SelectedBuildingActor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MainView extends BaseView {
    Map<MapTile, MapTileActor> mapTileActors;
    Player[] players;
    int tileWidth, tileHeight;

    SelectedBuildingActor selectedBuildingActor;

    public MainView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager, GameScreenController gameScreenController,
                    float viewportWidth, float viewportHeight) {
        super(startX, startY, width, height, batch, textureManager, viewportWidth, viewportHeight);
        this.initSubscriptions(gameScreenController);
        this.textureManager = textureManager;
        mapTileActors = new HashMap<>();
        TextButton button = new TextButton("main view", textureManager.skin);
        button.setPosition(10, 20);

        rootTable.addActor(button);
    }

    private void initSubscriptions(GameScreenController gameScreenController) {
        gameScreenController.subscribeOnChangeTile(new Consumer<MapTile>(){
            @Override
            public void accept(MapTile tile) {
                updateTile(tile);
            }
        });
        gameScreenController.subscribeOnSelectBuilding(new Consumer<Building>(){
            @Override
            public void accept(Building building) {
                selectBuilding(building);
            }
        });
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setMap(GameMap gameMap) {
        this.tileWidth = gameMap.getTileWidth();
        this.tileHeight = gameMap.getTileHeight();
        for (MapTile[] row: gameMap.getTiles()) {
            for (MapTile tile: row) {
                MapTileActor tileActor = new MapTileActor(textureManager, tile, players);
                tileActor.setBounds(tile.getX() * tileWidth, tile.getY() * tileHeight, tileWidth, tileHeight);
                mapTileActors.put(tile, tileActor);
                rootTable.addActor(tileActor);
            }
        }
    }

    private void updateTile(MapTile tile) {

    }

    private void selectBuilding(Building building) {
        if (building != null) {
            System.out.println(building);
            selectedBuildingActor = new SelectedBuildingActor(textureManager, building, tileWidth, tileHeight);
            rootTable.addActor(selectedBuildingActor);
        } else {
            rootTable.removeActor(selectedBuildingActor);
            selectedBuildingActor = null;
        }
    }

    @Override
    public void update(float delta) {
        if (selectedBuildingActor != null) {
            int mouseX = Gdx.input.getX() - (int)rootTable.getX();
            int mouseY = (int)camera.viewportHeight - Gdx.input.getY();

            int tileX = (mouseX / tileWidth) * tileWidth;
            int tileY = (mouseY / tileHeight) * tileHeight;
            selectedBuildingActor.setPosition(tileX, tileY);
        }
        super.update(delta);
    }
}
