package com.github.eoinf.screens.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.github.eoinf.screens.main.views.GameScreen;

public class GameOverScreen implements Screen {
    Stage stage;

    Label messageLabel;
    private Viewport viewport;

    public GameOverScreen(LudumDare42 ludumDare42, int viewportWidth, int viewportHeight, TextureManager textureManager) {
        viewport = new FitViewport(viewportWidth, viewportHeight);
        this.stage = new Stage(viewport);

        Table table = new Table();
        table.setFillParent(true);
        messageLabel = new Label("", textureManager.skin);

        TextButton button = new TextButton("Main Menu", textureManager.skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ludumDare42.switchToMainMenu();
                super.clicked(event, x, y);
            }
        });

        table.add(messageLabel);
        table.row();
        table.add(button);
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        viewport.apply(true);
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

    public void setPlayer(Player player) {
        if (player.getId() == GameScreen.HUMAN_PLAYER_ID) {
            this.messageLabel.setText("You lost!");
        } else {
            this.messageLabel.setText("You win!");
        }
    }
}
