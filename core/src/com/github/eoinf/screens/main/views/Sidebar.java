package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Sidebar extends BaseView {

    public Sidebar(int startX, int startY, int width, int height, Batch batch, Skin skin, float viewportWidth, float viewportHeight) {
        super(startX, startY, width, height, batch, skin, viewportWidth, viewportHeight, Color.BLACK);

        TextButton button = new TextButton("sidebar", skin);
        button.setPosition(10, 20);

        rootTable.addActor(button);
    }
}
