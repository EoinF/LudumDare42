package com.github.eoinf;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.game.Building;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static final String TILE_GRASS = "tiles/grass";
    private static final String TILE_BORDER = "tiles/border";
    private static final String TILE_BLANK = "tiles/blank";

    private static final String BUILDING_COTTAGE = "buildings/cottage";

    public TileTextures tiles;
    public BuildingTextures buildings;
    public Skin skin;

    public TextureManager(TextureAtlas atlas, Skin skin) {
        this.skin = skin;

        //
        // Tiles
        //
        this.tiles = new TileTextures();
        tiles.grass = loadRegion(atlas, TILE_GRASS);
        tiles.border = loadRegion(atlas, TILE_BORDER);
        tiles.blank = loadRegion(atlas, TILE_BLANK);

        //
        // Buildings
        //
        this.buildings = new BuildingTextures();
        buildings.setByType(Building.BuildingType.COTTAGE,
                loadRegion(atlas, BUILDING_COTTAGE));
        buildings.setByType(Building.BuildingType.BARRACKS,
                loadRegion(atlas, BUILDING_COTTAGE));
    }

    private TextureRegion loadRegion(TextureAtlas atlas, String index) {
        TextureRegion region = atlas.findRegion(index);
        if (region == null) {
            throw new RuntimeException("Failed to find texture with index " + index);
        }
        return region;
    }

    public class TileTextures {
        public TextureRegion grass;
        public TextureRegion border;
        public TextureRegion blank;
    }

    public class BuildingTextures {
        private Map<Building.BuildingType, TextureRegion> textureRegionMap;

        BuildingTextures() {
            this.textureRegionMap = new HashMap<>();
        }
        public TextureRegion getByType(Building.BuildingType type) {
            return textureRegionMap.get(type);
        }
        private void setByType(Building.BuildingType type, TextureRegion textureRegion) {
            textureRegionMap.put(type, textureRegion);
        }
    }
}
