package com.github.eoinf.game;

public class GameMap {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private MapTile[][] tiles;
    private Player[] players;

    private static final int TILE_OWNED_CUTOFF = 10;

    public GameMap(int width, int height, int tileWidth, int tileHeight, Player[] players) {
        this.players = players;
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.tiles = new MapTile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int ownerId = getOwnerAt(i, j);
                this.tiles[i][j] = new MapTile(i , j, ownerId);
            }
        }
    }

    public MapTile[][] getTiles() {
        return tiles;
    }

    private int getOwnerAt(int i, int j) {
        if (j >= 0 && j < TILE_OWNED_CUTOFF) {
            return players[0].getId();
        } else if (j <= height && j > height - TILE_OWNED_CUTOFF) {
            return players[1].getId();
        } else {
            return MapTile.NO_OWNER;
        }
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
