package com.github.eoinf.game;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

public class Player {
    private int id;
    private Color colour;

    public Resource people;
    public Resource soldier;
    public Resource metal;
    public Resource wood;

    public void resetResourceUsage() {
        people.delta = 0;
        soldier.delta = 0;
        metal.delta = 0;
        wood.delta = 0;
        people.used = 0;
        soldier.used = 0;
        metal.used = 0;
        wood.used = 0;
    }

    public Player(int id, Color colour, int startingPeople) {
        this.id = id;
        this.colour = colour;
        this.people = new Resource(startingPeople);
        this.soldier = new Resource(0);
        this.metal = new Resource(0);
        this.wood = new Resource(0);
        resetResourceUsage();
    }

    public int getId() {
        return id;
    }

    public Color getColour() {
        return colour;
    }

    public boolean canConstructBuilding(Building building) {
        return building.getPeopleRequired() <= people.amountAvailable();
    }

    public void collectNewResources() {
        this.people.total += this.people.delta;
        this.soldier.total += this.soldier.delta;
        this.metal.total += this.metal.delta;
        this.wood.total += this.wood.delta;
    }

    public void calculateNextResources(List<PlacedBuilding> buildings) {
        this.resetResourceUsage();
        for (PlacedBuilding placedBuilding: buildings) {
            if (placedBuilding.getOwner() == this.id && placedBuilding.isConstructed()) {
                placedBuilding.getBuilding().getEffect().applyTo(this);
            }
        }
    }
}
