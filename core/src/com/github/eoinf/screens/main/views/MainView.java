package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.widgets.MapTileActor;

import java.util.HashMap;
import java.util.Map;

public class MainView extends BaseView {
    Map<MapTile, MapTileActor> mapTileActors;
    TextureManager textureManager;
    Player[] players;

    public MainView(int startX, int startY, int width, int height, Batch batch, Skin skin,
                    float viewportWidth, float viewportHeight, TextureManager textureManager) {
        super(startX, startY, width, height, batch, skin, viewportWidth, viewportHeight);

        this.textureManager = textureManager;
        mapTileActors = new HashMap<>();
        TextButton button = new TextButton("main view", skin);
        button.setPosition(10, 20);

        rootTable.addActor(button);
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setMap(GameMap gameMap) {
        int tileWidth = gameMap.getTileWidth();
        int tileHeight = gameMap.getTileHeight();
        for (MapTile[] row: gameMap.getTiles()) {
            for (MapTile tile: row) {
                MapTileActor tileActor = new MapTileActor(textureManager, tile, players);
                tileActor.setBounds(tile.getX() * tileWidth, tile.getY() * tileHeight, tileWidth, tileHeight);
                mapTileActors.put(tile, tileActor);
                rootTable.addActor(tileActor);
            }
        }
    }

    public void updateTile(MapTile tile) {

    }
}
