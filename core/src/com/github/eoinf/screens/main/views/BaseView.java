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
    FitViewport viewport;
    OrthographicCamera camera;
    TextureManager textureManager;

    protected Table rootTable;

    public BaseView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager,
                    float viewportWidth, float viewportHeight) {
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        this.stage = new Stage(viewport, batch);
        this.textureManager = textureManager;
        initRootTable(startX, startY, width, height);
    }

    public BaseView(int startX, int startY, int width, int height,
                    Batch batch, TextureManager textureManager,
                    float viewportWidth, float viewportHeight,
                    Color colour) {
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        this.stage = new Stage(viewport, batch);
        this.textureManager = textureManager;
        initRootTable(startX, startY, width, height, colour);
    }

    private void initRootTable(int startX, int startY, int width, int height) {
        rootTable = new Table();
        rootTable.setBounds(startX, startY, width, height);
        stage.addActor(rootTable);
    }

    private void initRootTable(int startX, int startY, int width, int height, Color colour) {
        rootTable = new Table();
        rootTable.setBounds(startX, startY, width, height);
        utils.setBackgroundColour(rootTable, colour);
        stage.addActor(rootTable);
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }
}
