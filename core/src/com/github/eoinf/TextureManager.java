package com.github.eoinf;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {

    private static final String TILE_GRASS = "tiles/grass";
    private static final String TILE_BORDER = "tiles/border";

    public TileTextures tiles;

    public TextureManager(TextureAtlas atlas) {
        tiles = new TileTextures();
        tiles.grass = loadRegion(atlas, TILE_GRASS);
        tiles.border = loadRegion(atlas, TILE_BORDER);
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
    }
}
