package com.github.eoinf.game;

import com.badlogic.gdx.graphics.Color;

public class Player {
    private int id;
    private Color colour;

    public Player(int id, Color colour) {
        this.id = id;
        this.colour = colour;
    }

    public int getId() {
        return id;
    }

    public Color getColour() {
        return colour;
    }
}
