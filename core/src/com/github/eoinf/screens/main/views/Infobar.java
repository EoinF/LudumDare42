package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.Resource;
import com.github.eoinf.screens.main.controllers.GameScreenController;

import java.util.function.Consumer;

public class Infobar extends BaseView {
    // Hack to remove the massive amount of spacing around the labels
    private static final int LABEL_WIDTH = 50;
    private static final int CONTENT_WIDTH = 70;

    private Label personCountLabel;
    private Label personDeltaLabel;

    private Label soldierCountLabel;
    private Label soldierDeltaLabel;

    private Label metalCountLabel;
    private Label metalDeltaLabel;

    private Label woodCountLabel;
    private Label woodDeltaLabel;

    public Infobar(int startX, int startY, int width, int height, Batch batch, TextureManager textureManager,
                   GameScreenController gameScreenController, int humanPlayerId) {
        super(startX, startY, width, height, batch, textureManager, Color.FIREBRICK);
        rootTable.left();

        Label label = new Label("People: ", textureManager.skin);
        personCountLabel = new Label("0", textureManager.skin);
        personDeltaLabel = new Label("", textureManager.skin);
        rootTable.add(label).width(LABEL_WIDTH);
        rootTable.add(personCountLabel).width(CONTENT_WIDTH);
        rootTable.add(personDeltaLabel).width(CONTENT_WIDTH);

        label = new Label("Soldier: ", textureManager.skin);
        label.setWidth(LABEL_WIDTH);
        soldierCountLabel = new Label("0", textureManager.skin);
        soldierDeltaLabel = new Label("", textureManager.skin);
        rootTable.add(label).width(LABEL_WIDTH);
        rootTable.add(soldierCountLabel).width(CONTENT_WIDTH);
        rootTable.add(soldierDeltaLabel).width(CONTENT_WIDTH);

        label = new Label("Metal: ", textureManager.skin);
        label.setWidth(LABEL_WIDTH);
        metalCountLabel = new Label("0", textureManager.skin);
        metalDeltaLabel = new Label("", textureManager.skin);
        rootTable.add(label).width(LABEL_WIDTH);
        rootTable.add(metalCountLabel).width(CONTENT_WIDTH);
        rootTable.add(metalDeltaLabel).width(CONTENT_WIDTH);

        label = new Label("Wood: ", textureManager.skin);
        label.setWidth(LABEL_WIDTH);
        woodCountLabel = new Label("0", textureManager.skin);
        woodDeltaLabel = new Label("", textureManager.skin);
        rootTable.add(label).width(LABEL_WIDTH);
        rootTable.add(woodCountLabel).width(CONTENT_WIDTH);
        rootTable.add(woodDeltaLabel).width(CONTENT_WIDTH);

        TextButton endTurnButton = new TextButton("End Turn", textureManager.skin);

        endTurnButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touch up info bar " + humanPlayerId);
                gameScreenController.endPlayerTurn(humanPlayerId);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        rootTable.add(endTurnButton);

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
        personCountLabel.setText(formatResourceText(player.people));
        updateDeltaLabel(personDeltaLabel, player.people.delta);

        soldierCountLabel.setText(formatResourceText(player.soldier));
        updateDeltaLabel(soldierDeltaLabel, player.soldier.delta);

        metalCountLabel.setText(formatResourceText(player.metal));
        updateDeltaLabel(metalDeltaLabel, player.metal.delta);

        woodCountLabel.setText(formatResourceText(player.wood));
        updateDeltaLabel(woodDeltaLabel, player.wood.delta);
    }

    private void updateDeltaLabel(Label deltaLabel, int delta) {
        String text = "( ";
        if (delta > 0) {
            text += "+";
            deltaLabel.setColor(Color.GREEN);
        } else if (delta < 0) {
            text += "-";
            deltaLabel.setColor(Color.BLUE);
        } else {
            text += "+";
            deltaLabel.setColor(Color.WHITE);
        }
        text += (delta + " )");

        deltaLabel.setText(text);
    }

    private String formatResourceText(Resource resource) {
        return resource.amountAvailable() + "/" + resource.total;
    }
}
