package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.eoinf.TextureManager;
import com.github.eoinf.utils;

public abstract class BaseView {
    Stage stage;
    private FitViewport viewport;
    OrthographicCamera camera;
    TextureManager textureManager;
    protected int screenX, screenY;

    protected Table rootTable;

    public BaseView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager) {
        this.screenX = startX;
        this.screenY = startY;
        this.camera = new OrthographicCamera(width, height);
        this.viewport = new FitViewport(width, height, camera);
        this.stage = new Stage(viewport, batch);
        viewport.setScreenBounds(startX, startY, width, height);
        this.textureManager = textureManager;
        initRootTable();
    }

    public BaseView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager,
                    Color colour) {
        this.screenX = startX;
        this.screenY = startY;
        this.camera = new OrthographicCamera(width, height);
        this.viewport = new FitViewport(width, height, camera);
        this.stage = new Stage(viewport, batch);
        viewport.setScreenBounds(startX, startY, width, height);
        this.textureManager = textureManager;
        initRootTable(colour);
    }

    private void initRootTable() {
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
    }

    private void initRootTable(Color colour) {
        rootTable = new Table();
        rootTable.setFillParent(true);
        utils.setBackgroundColour(rootTable, colour);
        stage.addActor(rootTable);
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void render() {
        viewport.apply();
        viewport.setScreenBounds(screenX, screenY, viewport.getScreenWidth(), viewport.getScreenHeight());

        stage.draw();
    }
}
