package com.github.eoinf.game;

import com.badlogic.gdx.graphics.Color;

public class Player {
    private int id;
    private Color colour;
    public int peopleCount;
    public int soliderCount;
    public int metalCount;
    public int woodCount;

    public void resetResourceUsage() {
        peopleUsed = 0;
        soliderUsed = 0;
        metalUsed = 0;
        woodUsed = 0;
    }
    //
    // Resources already utilized this turn
    //
    public int peopleUsed;
    public int soliderUsed;
    public int metalUsed;
    public int woodUsed;

    public Player(int id, Color colour, int startingPeople) {
        this.id = id;
        this.colour = colour;
        this.peopleCount = startingPeople;
        this.soliderCount = 0;
        this.metalCount = 0;
        this.woodCount = 0;
        resetResourceUsage();
    }

    public int getId() {
        return id;
    }

    public Color getColour() {
        return colour;
    }

    public boolean canConstructBuilding(Building building) {
        return building.getPeopleRequired() < peopleCount - peopleUsed;
    }
}
