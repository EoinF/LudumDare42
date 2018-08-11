package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.BuildingCategory;

import java.util.Map;
import java.util.function.Consumer;

public class GameScreen implements Screen {

    MainView mainView;
    Sidebar sidebar;
    Infobar infoBar;

    GameMap gameMap;
    GameScreenController gameScreenController;

    private static final int SIDEBAR_WIDTH = 256;
    private static final int INFO_BAR_WIDTH = 416;
    private static final int INFO_BAR_HEIGHT = 50;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    public GameScreen(int viewportWidth, int viewportHeight, Batch batch, TextureManager textureManager,
                      Map<BuildingCategory, Building[]> buildingCategoryMap) {
        Player[] players = new Player[2];
        players[0] = new Player(0, Color.BLUE);
        players[1] = new Player(1, Color.RED);

        this.gameMap = new GameMap(32, 25, TILE_WIDTH, TILE_HEIGHT, players);
        this.gameScreenController = new GameScreenController();
        this.mainView = new MainView(SIDEBAR_WIDTH, 0, viewportWidth, viewportHeight,
                batch, textureManager, gameScreenController,
                viewportWidth, viewportHeight);
        this.sidebar = new Sidebar(0, 0, SIDEBAR_WIDTH, viewportHeight,
                batch, textureManager, gameScreenController,
                viewportWidth, viewportHeight);
        this.infoBar = new Infobar(viewportWidth - INFO_BAR_WIDTH, viewportHeight - INFO_BAR_HEIGHT,
                INFO_BAR_WIDTH, INFO_BAR_HEIGHT,
                batch, textureManager,
                viewportWidth, viewportHeight);

        this.mainView.setPlayers(players);
        this.mainView.setMap(gameMap);

        this.sidebar.setBuildings(buildingCategoryMap, TILE_WIDTH, TILE_HEIGHT);


        InputMultiplexer mux = new InputMultiplexer(
                this.mainView.stage,
                this.sidebar.stage
        );
        Gdx.input.setInputProcessor(mux);
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        mainView.update(delta);
        sidebar.update(delta);
        infoBar.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        mainView.render();
        infoBar.render();
        sidebar.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
