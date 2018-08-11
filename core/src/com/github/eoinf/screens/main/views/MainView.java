package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainView extends BaseView {
    public MainView(int startX, int startY, int width, int height, Batch batch, Skin skin,
                    float viewportWidth, float viewportHeight) {
        super(startX, startY, width, height, batch, skin, viewportWidth, viewportHeight);

        TextButton button = new TextButton("main view", skin);
        button.setPosition(10, 20);

        rootTable.addActor(button);
    }
}
