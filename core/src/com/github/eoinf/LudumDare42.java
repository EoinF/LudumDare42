package com.github.eoinf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.BuildingEffect;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.Unit;
import com.github.eoinf.screens.gameover.GameOverScreen;
import com.github.eoinf.screens.title.MainMenuScreen;
import com.github.eoinf.screens.main.views.GameScreen;
import com.github.eoinf.screens.main.widgets.BuildingCategory;

import java.util.HashMap;
import java.util.Map;

public class LudumDare42 extends Game {
    SpriteBatch batch;
    TextureManager textureManager;

    private static final int VIEWPORT_WIDTH = 1280;
    private static final int VIEWPORT_HEIGHT = 720;
    private GameOverScreen gameOverScreen;
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create() {
        FileHandle f = Gdx.files.internal("skin/Holo-dark-hdpi.json");
        Skin skin = new Skin(f);
        skin.getFont("default-font").getData().setScale(0.5f, 0.5f);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/game.atlas"));
        textureManager = new TextureManager(atlas, skin);
        batch = new SpriteBatch();

        gameOverScreen = new GameOverScreen(this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, textureManager);
        mainMenuScreen = new MainMenuScreen(this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, textureManager);

        setScreen(mainMenuScreen);
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

    private Unit[] getUnitTypes() {
        final String VANDAL_DESCRIPTION = "Cost: 8 soldier (Can only raze buildings)";
        final String ARCHER_DESCRIPTION = "Cost: 20 soldier (Can attack from a distance)";
        final String SWORDSMAN_DESCRIPTION = "Cost: 15 soldier (Always attacks before move phase)";
        return new Unit[] {
                new Unit("Vandal", Unit.WeaponType.NONE, VANDAL_DESCRIPTION,
                        8, 0, 0),
                new Unit("Swordsman", Unit.WeaponType.SWORD, SWORDSMAN_DESCRIPTION,
                        15, 0, 0),
                new Unit("Archer", Unit.WeaponType.BOW_AND_ARROW, ARCHER_DESCRIPTION,
                        20, 0, 0)
        };
    }

    private Map<BuildingCategory, Building[]> getBuildingCategories() {
        Map<BuildingCategory, Building[]> categoryMap = new HashMap<>();

        final String COTTAGE_DESCRIPTION = "Cost: 1 person (+ 1 person rate; Use people to build and to train as soldiers)";

        final String BARRACKS_DESCRIPTION = "Cost: 5 people (- 2 person rate, + 1 soldier rate; Soldiers can be deployed and roam the map)";
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
                barracks,
        });
        return categoryMap;
    }

    public void switchToGameOver(Player player) {
        gameOverScreen.setPlayer(player);
        setScreen(gameOverScreen);
    }

    public void switchToMainMenu() {
        setScreen(mainMenuScreen);
    }

    public void switchToGame() {
        Screen gameScreen = new GameScreen(this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, batch, textureManager, getBuildingCategories(),
                getUnitTypes());
        setScreen(gameScreen);
    }
}
