package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.eoinf.TextureManager;

public class Infobar extends BaseView {

    public Infobar(int startX, int startY, int width, int height, Batch batch, TextureManager textureManager) {
        super(startX, startY, width, height, batch, textureManager, Color.FIREBRICK);
    }
}
