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
	public void create () {
        FileHandle f = Gdx.files.internal("skin/Holo-dark-hdpi.json");
		Skin skin = new Skin(f);
		skin.getFont("default-font").getData().setScale(0.5f,0.5f);
		TextureAtlas atlas = new TextureAtlas(new FileHandle("textures/game.atlas"));
        TextureManager textureManager = new TextureManager(atlas, skin);
        batch = new SpriteBatch();
		gameScreen = new GameScreen(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, batch, textureManager, getBuildingCategories());
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private Map<BuildingCategory, Building[]> getBuildingCategories() {
		Map<BuildingCategory, Building[]> categoryMap = new HashMap<>();

		categoryMap.put(BuildingCategory.HOUSING, new Building[]{
				new Building("Cottage", new GridPoint2[] {
						new GridPoint2(0, 0),
						new GridPoint2(1, 0),
						new GridPoint2(0, 1),
						new GridPoint2(1, 1)
				}, Building.BuildingType.COTTAGE)
		});

		categoryMap.put(BuildingCategory.MILITARY, new Building[]{
				new Building("Barracks", new GridPoint2[] {
						new GridPoint2(0, 0),
						new GridPoint2(1, 0),
						new GridPoint2(2, 0),
						new GridPoint2(3, 0),
						new GridPoint2(0, 1),
						new GridPoint2(1, 1),
						new GridPoint2(2, 1),
						new GridPoint2(3, 1)
				}, Building.BuildingType.COTTAGE)
		});
		return categoryMap;
	}
}
