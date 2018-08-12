package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

/**
 * A blueprint for a unit (soldier)
 */
public class Unit implements MapObjectBlueprint {
    public WeaponType getWeapon() {
        return weapon;
    }

    @Override
    public GridPoint2[] getShape() {
        return DEFAULT_SHAPE;
    }

    GridPoint2[] DEFAULT_SHAPE = new GridPoint2[] {
            new GridPoint2(0, 0)
    };

    private String name;
    private WeaponType weapon;
    private String description;
    private int soldierCost;
    private int metalCost;
    private int woodCost;

    public Unit(String name, WeaponType weapon, String description, int soldierCost, int metalCost, int woodCost) {
        this.name = name;
        this.weapon = weapon;
        this.description = description;

        this.soldierCost = soldierCost;
        this.metalCost = metalCost;
        this.woodCost = woodCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSoldierCost() {
        return soldierCost;
    }

    public int getMetalCost() {
        return metalCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public enum WeaponType {
        NONE,
        SWORD,
        BOW_AND_ARROW
    }
}
