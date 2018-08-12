package com.github.eoinf.game;

public abstract class BuildingEffect {

    public String description;
    public BuildingEffect(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void applyTo(Player player);
}
