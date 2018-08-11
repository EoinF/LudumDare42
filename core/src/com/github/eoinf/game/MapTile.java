package com.github.eoinf.game;

public class MapTile {
    private int x;
    private int y;

    // The id of the player owning this tile
    private int ownerId;
    public static final int NO_OWNER = -1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MapTile(int x, int y, int ownerId) {
        this.x = x;
        this.y = y;
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
