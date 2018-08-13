package com.github.eoinf.game;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

public class Player {
    public boolean isAlive;
    private int id;
    private Color colour;

    public Resource people;
    public Resource soldier;
    public Resource metal;
    public Resource wood;

    public void resetResourceUsage() {
        // People are the only resource that doesn't deplete
        people.delta = 0;
        people.used = 0;

        soldier.total -= soldier.used;
        soldier.delta = 0;
        soldier.used = 0;

        metal.total -= metal.used;
        metal.delta = 0;
        metal.used = 0;

        wood.total -= wood.used;
        wood.delta = 0;
        wood.used = 0;
    }

    public Player(int id, Color colour, int startingPeople) {
        this.id = id;
        this.colour = colour;
        this.people = new Resource(startingPeople);
        this.soldier = new Resource(0);
        this.metal = new Resource(0);
        this.wood = new Resource(0);
        this.isAlive = true;
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

    public boolean canCreateUnit(Unit unit) {
        return unit.getSoldierCost() <= soldier.amountAvailable()
                && unit.getWoodCost() <= wood.amountAvailable()
                && unit.getMetalCost() <= metal.amountAvailable();
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
