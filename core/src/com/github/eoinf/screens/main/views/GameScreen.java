package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.screens.main.controllers.GameScreenController;

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


    public GameScreen(int viewportWidth, int viewportHeight, Batch batch, Skin skin, TextureManager textureManager) {
        this.gameMap = new GameMap(32, 30, TILE_WIDTH, TILE_HEIGHT);
        this.gameScreenController = new GameScreenController();
        this.mainView = new MainView(SIDEBAR_WIDTH, 0, viewportWidth, viewportHeight,
                batch, skin,
                viewportWidth, viewportHeight, textureManager);
        this.sidebar = new Sidebar(0, 0, SIDEBAR_WIDTH, viewportHeight,
                batch, skin,
                viewportWidth, viewportHeight);
        this.infoBar = new Infobar(viewportWidth - INFO_BAR_WIDTH, viewportHeight - INFO_BAR_HEIGHT,
                INFO_BAR_WIDTH, INFO_BAR_HEIGHT,
                batch, skin,
                viewportWidth, viewportHeight);

        this.mainView.setMap(gameMap);

        gameScreenController.subscribeOnChangeTile(new Consumer<MapTile>(){
            @Override
            public void accept(MapTile tile) {
                mainView.updateTile(tile);
            }
        });
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
