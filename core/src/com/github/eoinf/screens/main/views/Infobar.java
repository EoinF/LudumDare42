package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Infobar extends BaseView {

    public Infobar(int startX, int startY, int width, int height, Batch batch, Skin skin, float viewportWidth, float viewportHeight) {
        super(startX, startY, width, height, batch, skin, viewportWidth, viewportHeight, Color.BLACK);
    }
}
