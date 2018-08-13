package com.github.eoinf.screens.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.eoinf.LudumDare42;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Player;

public class MainMenuScreen implements Screen {
    Stage stage;

    Label messageLabel;

    public MainMenuScreen(LudumDare42 ludumDare42, int viewportWidth, int viewportHeight, TextureManager textureManager) {
        Viewport viewport = new FitViewport(viewportWidth, viewportHeight);
        this.stage = new Stage(viewport);

        Table table = new Table();
        table.setFillParent(true);
        Label title = new Label("Tribal Space", textureManager.skin);
        title.setFontScale(2);
        table.add(title);
        table.row();

        table.add(new Label("Instructions", textureManager.skin));
        table.row();
        table.add(new Label("The player with no buildings left loses", textureManager.skin));
        table.row();

        table.add(new Label("Your base is at the bottom of the screen", textureManager.skin));
        table.row();
        table.add(new Label("You can only create units and buildings in your base", textureManager.skin));
        table.row();
        table.add(new Label("Buildings cost *people*", textureManager.skin));
        table.row();
        table.add(new Label("Units cost *soldiers*", textureManager.skin));
        table.row();
        TextButton button = new TextButton("Start", textureManager.skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ludumDare42.switchToGameOver(new Player(0, Color.BLUE,0));
                ludumDare42.switchToGame();
                super.clicked(event, x, y);
            }
        });

        table.add(messageLabel);
        table.add(button);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
