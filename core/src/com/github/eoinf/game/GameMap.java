package com.github.eoinf.game;

public class GameMap {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    private MapTile[][] tiles;

    public GameMap(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.tiles = new MapTile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.tiles[i][j] = new MapTile(i , j);
            }
        }
    }

    public MapTile[][] getTiles() {
        return tiles;
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
