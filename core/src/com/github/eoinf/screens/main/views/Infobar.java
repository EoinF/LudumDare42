package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;

import java.util.function.Consumer;

public class Infobar extends BaseView {
    private Label personCountLabel;
    private Label soldierCountLabel;
    private Label ironCountLabel;
    private Label woodCountLabel;

    public Infobar(int startX, int startY, int width, int height, Batch batch, TextureManager textureManager,
                   GameScreenController gameScreenController, int humanPlayerId) {
        super(startX, startY, width, height, batch, textureManager, Color.FIREBRICK);
        rootTable.left();

        Label label = new Label("People: ", textureManager.skin);
        personCountLabel = new Label("0", textureManager.skin);
        rootTable.add(label);
        rootTable.add(personCountLabel);

        label = new Label("Soldier: ", textureManager.skin);
        soldierCountLabel = new Label("0", textureManager.skin);
        rootTable.add(label);
        rootTable.add(soldierCountLabel);

        label = new Label("Iron: ", textureManager.skin);
        ironCountLabel = new Label("0", textureManager.skin);
        rootTable.add(label);
        rootTable.add(ironCountLabel);

        label = new Label("Wood: ", textureManager.skin);
        woodCountLabel = new Label("0", textureManager.skin);
        rootTable.add(label);
        rootTable.add(woodCountLabel);

        gameScreenController.subscribeOnChangePlayer(new Consumer<Player>() {
            @Override
            public void accept(Player player) {
                if (player.getId() == humanPlayerId) {
                    setPlayer(player);
                }
            }
        });
    }

    public void setPlayer(Player player) {

    }
}
