package com.github.eoinf.screens.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.screens.main.views.Infobar;
import com.github.eoinf.screens.main.views.MainView;
import com.github.eoinf.screens.main.views.Sidebar;

public class GameScreen implements Screen {

    MainView mainView;
    Sidebar sidebar;
    Infobar infoBar;

    private static final int SIDEBAR_WIDTH = 250;
    private static final int INFO_BAR_WIDTH = 400;
    private static final int INFO_BAR_HEIGHT = 50;


    public GameScreen(int viewportWidth, int viewportHeight, Batch batch, Skin skin) {
        this.mainView = new MainView(SIDEBAR_WIDTH, 0, viewportWidth, viewportHeight,
                batch, skin,
                viewportWidth, viewportHeight);
        this.sidebar = new Sidebar(0, 0, SIDEBAR_WIDTH, viewportHeight,
                batch, skin,
                viewportWidth, viewportHeight);
        this.infoBar = new Infobar(viewportWidth - INFO_BAR_WIDTH, viewportHeight - INFO_BAR_HEIGHT,
                INFO_BAR_WIDTH, INFO_BAR_HEIGHT,
                batch, skin,
                viewportWidth, viewportHeight);
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
        infoBar.render();
        mainView.render();
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
