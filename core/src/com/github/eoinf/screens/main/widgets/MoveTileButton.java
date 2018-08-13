package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;

public abstract class MoveTileButton extends Image implements ActionActor {
    private static final Color MOVE_COLOUR = new Color(0, 0.3f, 1, 0.5f);
    MoveTileButton(TextureManager textureManager) {
        super(textureManager.tiles.blank);
        setColor(MOVE_COLOUR);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (super.hit(x, y, touchable) != null) {
            return this;
        } else {
            return null;
        }
    }
}
