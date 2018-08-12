package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.MapObjectBlueprint;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedObjectActor extends Group {
    private Map<GridPoint2, SelectedBuildingTile> tilesMap;
    private GameMap gameMap;
    private List<PlacedBuilding> placedBuildings;
    private Player humanPlayer;
    private TextureManager textureManager;

    private float timer = 0;
    private boolean isValidConstructionSite;

    public SelectedObjectActor(TextureManager textureManager, Player humanPlayer) {
        this.textureManager = textureManager;
        setTouchable(Touchable.disabled);
        this.isValidConstructionSite = false;
        this.humanPlayer = humanPlayer;
        this.placedBuildings = new ArrayList<>();
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void addNewPlacedBuilding(PlacedBuilding constructedBuilding) {
        this.placedBuildings.add(constructedBuilding);
    }

    public void setPlacedBuildings(List<PlacedBuilding> placedBuildings) {
        this.placedBuildings = placedBuildings;
    }

    private void setBuilding(Building building) {
        this.clear();
        if (building == null) {
            setUserObject(null);
        } else {
            tilesMap = new HashMap<>();
            setUserObject(building);
            addActor(new Image(textureManager.buildings.getByType(building.getType())));
        }
    }

    private void setUnit(Unit unit) {
        this.clear();
        if (unit == null) {
            setUserObject(null);
        } else {
            tilesMap = new HashMap<>();
            setUserObject(unit);
            addActor(new UnitActor(textureManager, unit, humanPlayer.getColour()));
        }
    }

    public void setTileXY(int x, int y) {
        Object object = getUserObject();
        if (object != null) {
            MapObjectBlueprint blueprint = (MapObjectBlueprint) object;
            this.setPosition(x * gameMap.getTileWidth(), y * gameMap.getTileHeight());

            this.isValidConstructionSite = true;
            for (GridPoint2 point : blueprint.getShape()) {
                MapTile tile = gameMap.getTile(x + point.x, y + point.y);
                if (tile == null || !tile.isOwnedBy(humanPlayer.getId())
                        || (object instanceof Building && hasBuilding(tile)) // This check is only required for placing buildings
                        ) {
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
        for (PlacedBuilding constructedBuilding : placedBuildings) {
            if (constructedBuilding.containsTile(tile)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidConstructionSite() {
        return this.isValidConstructionSite;
    }

    public void setObject(MapObjectBlueprint object) {
        if (object instanceof Building) {
            setBuilding((Building) object);
        } else {
            setUnit((Unit)object);
        }
        if (object != null) {
            for (GridPoint2 tileOccupied : object.getShape()) {
                SelectedBuildingTile selectedBuildingTile = new SelectedBuildingTile(textureManager.tiles.blank,
                        tileOccupied.x * gameMap.getTileWidth(),
                        tileOccupied.y * gameMap.getTileHeight());

                addActor(selectedBuildingTile);
                tilesMap.put(tileOccupied, selectedBuildingTile);
            }
        }
    }

    public void removePlacedBuilding(PlacedBuilding placedBuilding) {
        placedBuildings.remove(placedBuilding);
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
