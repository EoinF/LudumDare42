package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedBuildingActor extends Group {
    private Map<GridPoint2, SelectedBuildingTile> tilesMap;
    private GameMap gameMap;
    private List<PlacedBuilding> constructedBuildings;
    private int playerId;
    private TextureManager textureManager;

    private float timer = 0;
    private boolean isValidConstructionSite;

    public SelectedBuildingActor(TextureManager textureManager, int playerId) {
        this.textureManager = textureManager;
        setTouchable(Touchable.disabled);
        this.isValidConstructionSite = false;
        this.playerId = playerId;
        this.constructedBuildings = new ArrayList<>();
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void addNewPlacedBuilding(PlacedBuilding constructedBuilding) {
        this.constructedBuildings.add(constructedBuilding);
    }

    public void setConstructedBuildings(List<PlacedBuilding> constructedBuildings) {
        this.constructedBuildings = constructedBuildings;
    }

    public void setBuilding(Building building) {
        this.clear();
        if (building == null) {
            setUserObject(null);
        } else {
            setUserObject(building);
            addActor(new Image(textureManager.buildings.getByType(building.getType())));

            tilesMap = new HashMap<>();
            for (GridPoint2 tileOccupied : building.getShape()) {
                SelectedBuildingTile selectedBuildingTile = new SelectedBuildingTile(textureManager.tiles.blank,
                        tileOccupied.x * gameMap.getTileWidth(),
                        tileOccupied.y * gameMap.getTileHeight());

                addActor(selectedBuildingTile);
                tilesMap.put(tileOccupied, selectedBuildingTile);
            }
        }
    }

    public void setTileXY(int x, int y) {
        Building building = (Building) getUserObject();
        if (building != null) {
            this.setPosition(x * gameMap.getTileWidth(), y * gameMap.getTileHeight());

            this.isValidConstructionSite = true;
            for (GridPoint2 point : building.getShape()) {
                MapTile tile = gameMap.getTile(x + point.x, y + point.y);
                if (tile == null || !tile.isOwnedBy(playerId) || hasBuilding(tile)) {
                    tilesMap.get(point).setIsValid(false);
                    this.isValidConstructionSite = false;
                } else {
                    tilesMap.get(point).setIsValid(true);
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        Object userObject = getUserObject();
        if (userObject != null) {
            timer += delta;
            float r = 0.8f + 0.2f * (float) Math.sin(4 * timer);
            float g = 0.8f + 0.2f * (float) Math.sin(4 * timer);
            float b = 0.6f;

            for (SelectedBuildingTile tile : this.tilesMap.values()) {
                tile.setHighlight(r, g, b, 0.4f);
            }
            super.act(delta);
        }
    }

    private boolean hasBuilding(MapTile tile) {
        for (PlacedBuilding constructedBuilding: constructedBuildings) {
            if (constructedBuilding.containsTile(tile)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidConstructionSite() {
        return this.isValidConstructionSite;
    }

    class SelectedBuildingTile extends Image {
        private Color highlight;
        private Color validityColour;

        public SelectedBuildingTile(TextureRegion region, float x, float y) {
            super(region);
            this.highlight = Color.WHITE.cpy();
            this.validityColour = Color.WHITE.cpy();
            setPosition(x, y);
        }

        public void setHighlight(float r, float g, float b, float a) {
            this.highlight.r = r;
            this.highlight.g = g;
            this.highlight.b = b;
            this.highlight.a = a;
            this.setColor(validityColour.cpy().mul(highlight));
        }

        public void setIsValid(boolean isValid) {
            if (isValid) {
                validityColour = Color.GREEN.cpy();
            } else {
                validityColour = Color.RED.cpy();
            }
            this.setColor(validityColour.cpy().mul(highlight));
        }
    }
}
