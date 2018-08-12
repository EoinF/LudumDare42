package com.github.eoinf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.BuildingEffect;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.views.GameScreen;
import com.github.eoinf.screens.main.widgets.BuildingCategory;

import java.util.HashMap;
import java.util.Map;

public class LudumDare42 extends Game {
    SpriteBatch batch;
    GameScreen gameScreen;

    private static final int VIEWPORT_WIDTH = 1280;
    private static final int VIEWPORT_HEIGHT = 720;

    @Override
    public void create() {
        FileHandle f = Gdx.files.internal("skin/Holo-dark-hdpi.json");
        Skin skin = new Skin(f);
        skin.getFont("default-font").getData().setScale(0.5f, 0.5f);
        TextureAtlas atlas = new TextureAtlas(new FileHandle("textures/game.atlas"));
        TextureManager textureManager = new TextureManager(atlas, skin);
        batch = new SpriteBatch();
        gameScreen = new GameScreen(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, batch, textureManager, getBuildingCategories());
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private Map<BuildingCategory, Building[]> getBuildingCategories() {
        Map<BuildingCategory, Building[]> categoryMap = new HashMap<>();

        final String COTTAGE_DESCRIPTION = "+ 1 person rate (Use people to build and to train as soldiers)";
        final String BARRACKS_DESCRIPTION = "- 2 person rate, + 1 soldier rate (Soldiers can be deployed and roam the map)";

        //
        // Housing
        //
        Building cottage = new Building("Cottage", new GridPoint2[]{
                new GridPoint2(0, 0),
                new GridPoint2(1, 0),
                new GridPoint2(0, 1),
                new GridPoint2(1, 1)
        }, Building.BuildingType.COTTAGE, new BuildingEffect(COTTAGE_DESCRIPTION) {
            @Override
            public void applyTo(Player player) {
                player.people.delta++;
            }
        }, 1);

        categoryMap.put(BuildingCategory.HOUSING, new Building[]{
                cottage
        });

        //
        // Military
        //
        Building barracks = new Building("Barracks", new GridPoint2[]{
                new GridPoint2(0, 0),
                new GridPoint2(1, 0),
                new GridPoint2(2, 0),
                new GridPoint2(3, 0),
                new GridPoint2(0, 1),
                new GridPoint2(1, 1),
                new GridPoint2(2, 1),
                new GridPoint2(3, 1)
        }, Building.BuildingType.BARRACKS, new BuildingEffect(BARRACKS_DESCRIPTION) {
            @Override
            public void applyTo(Player player) {
                player.people.delta -= 2;
                player.soldier.delta++;
            }
        }, 5);

        categoryMap.put(BuildingCategory.MILITARY, new Building[]{
                barracks
        });
        return categoryMap;
    }
}
