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
    private static final String BUILDING_BARRACKS = "buildings/barracks";


    private static final String ICON_BUILDINGS = "ui/iconBuildings";
    private static final String ICON_PRODUCTION = "ui/iconProduction";
    private static final String ICON_UNITS = "ui/iconUnits";

    private static final String UNIT_BASIC_CLOTHES = "units/basicClothes";
    private static final String UNIT_BASIC_CORE = "units/basicCore";
    private static final String UNIT_WEAPON_BOW = "units/weaponBow";
    private static final String UNIT_WEAPON_SWORD = "units/weaponSword";

    public TileTextures tiles;
    public BuildingTextures buildings;
    public UITextures ui;
    public UnitTextures units;
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
                loadRegion(atlas, BUILDING_BARRACKS));

        //
        // UI
        //
        this.ui = new UITextures();
        ui.iconBuildings = loadRegion(atlas, ICON_BUILDINGS);
        ui.iconProduction = loadRegion(atlas, ICON_PRODUCTION);
        ui.iconUnits = loadRegion(atlas, ICON_UNITS);

        //
        // Units
        //
        this.units = new UnitTextures();
        units.basicCore = loadRegion(atlas, UNIT_BASIC_CORE);
        units.basicClothes = loadRegion(atlas, UNIT_BASIC_CLOTHES);
        units.weaponBow = loadRegion(atlas, UNIT_WEAPON_BOW);
        units.weaponSword = loadRegion(atlas, UNIT_WEAPON_SWORD);
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

    public class UITextures {
        public TextureRegion iconBuildings;
        public TextureRegion iconProduction;
        public TextureRegion iconUnits;
    }

    public class UnitTextures {
        public TextureRegion basicCore;
        public TextureRegion basicClothes;
        public TextureRegion weaponBow;
        public TextureRegion weaponSword;

    }
}
