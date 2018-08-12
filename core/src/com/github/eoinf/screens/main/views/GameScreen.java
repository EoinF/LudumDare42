package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.AIController;
import com.github.eoinf.game.Building;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.game.Player;
import com.github.eoinf.game.StateManager;
import com.github.eoinf.game.Unit;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.BuildingCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameScreen implements Screen {

    MainView mainView;
    Sidebar sidebar;
    Infobar infoBar;

    GameScreenController gameScreenController;
    StateManager stateManager;
    AIController ai;

    private static final int SIDEBAR_WIDTH = 256;
    private static final int INFO_BAR_WIDTH = 900;
    private static final int INFO_BAR_HEIGHT = 32;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private static final int HUMAN_PLAYER_ID = 0;
    private static final int AI_PLAYER_ID = 1;

    public GameScreen(int viewportWidth, int viewportHeight, Batch batch, TextureManager textureManager,
                      Map<BuildingCategory, Building[]> buildingCategoryMap, Unit[] unitTypes) {
        Player[] players = new Player[2];
        players[0] = new Player(HUMAN_PLAYER_ID, Color.BLUE, 1000);
        players[1] = new Player(AI_PLAYER_ID, Color.RED, 1);

        List<PlacedBuilding> placedBuildings = new ArrayList<>();
        List<PlacedUnit> placedUnits = new ArrayList<>();

        GameMap gameMap = new GameMap(32, 25, TILE_WIDTH, TILE_HEIGHT, players);
        this.gameScreenController = new GameScreenController(gameMap, players);

        stateManager = new StateManager(players, gameMap, placedBuildings, placedUnits, gameScreenController);

        ai = new AIController(AI_PLAYER_ID, players, placedBuildings, stateManager, gameScreenController);

        createViews(batch, textureManager, viewportWidth, viewportHeight, buildingCategoryMap, unitTypes);

        startGame();
    }

    private void createViews(Batch batch, TextureManager textureManager, int viewportWidth, int viewportHeight,
                             Map<BuildingCategory, Building[]> buildingCategoryMap, Unit[] unitTypes) {

        this.sidebar = new Sidebar(0, 0, SIDEBAR_WIDTH, viewportHeight,
                batch, textureManager, gameScreenController);
        this.infoBar = new Infobar(viewportWidth - INFO_BAR_WIDTH, viewportHeight - INFO_BAR_HEIGHT,
                INFO_BAR_WIDTH, INFO_BAR_HEIGHT,
                batch, textureManager, gameScreenController, HUMAN_PLAYER_ID);

        this.sidebar.setBuildingTemplates(buildingCategoryMap, TILE_WIDTH, TILE_HEIGHT);

        for (Player player: stateManager.getPlayers()) {
            if (player.getId() == HUMAN_PLAYER_ID) {
                this.mainView = new MainView(SIDEBAR_WIDTH, 0, viewportWidth - SIDEBAR_WIDTH, viewportHeight,
                        batch, textureManager, gameScreenController, player);
                this.infoBar.setPlayer(player);
                this.sidebar.setUnitTemplates(unitTypes, TILE_WIDTH, TILE_HEIGHT, player.getColour());
            }
        }

        this.mainView.setPlayers(stateManager.getPlayers());
        this.mainView.setMap(stateManager.getMap());
        this.mainView.setBuildings(stateManager.getBuildings());

        InputMultiplexer mux = new InputMultiplexer(
                this.infoBar.stage,
                this.mainView.stage,
                this.sidebar.stage
        );
        Gdx.input.setInputProcessor(mux);
    }

    private void startGame() {
        gameScreenController.subscribeOnPlaceBuilding(new Consumer<PlacedBuilding>() {
            @Override
            public void accept(PlacedBuilding constructedBuilding) {
                for (Player player: stateManager.getPlayers()) {
                    if (constructedBuilding.getOwner() == player.getId()) {
                        player.people.used += constructedBuilding.getBuilding().getPeopleRequired();
                        gameScreenController.changePlayer(player);
                        break;
                    }
                }
            }
        });
        gameScreenController.subscribeOnPlaceUnit(new Consumer<PlacedUnit>() {
            @Override
            public void accept(PlacedUnit placedUnit) {
                for (Player player: stateManager.getPlayers()) {
                    if (placedUnit.getOwner() == player.getId()) {
                        player.soldier.used += placedUnit.getUnit().getSoldierCost();
                        player.metal.used += placedUnit.getUnit().getMetalCost();
                        player.wood.used += placedUnit.getUnit().getWoodCost();
                        gameScreenController.changePlayer(player);
                        break;
                    }
                }
            }
        });
    }

    private void spawnTrees() {

    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        mainView.update(delta);
        sidebar.update(delta);
        infoBar.update(delta);

        stateManager.update();
        ai.update();
    }

    @Override
    public void render(float delta) {
        update(delta);
        mainView.render();
        infoBar.render();
        sidebar.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
