package com.github.eoinf.game;

import com.github.eoinf.screens.main.controllers.GameScreenController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.github.eoinf.game.StateManager.State.ACTION_PHASE;

public class StateManager {
    private GameMap gameMap;
    private Player[] players;
    private List<ConstructedBuilding> buildings;


    private Set<Integer> playerIdTurnEnded;
    private GameScreenController gameScreenController;
    private State state;

    public GameMap getMap() {
        return gameMap;
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<ConstructedBuilding> getBuildings() {
        return buildings;
    }


    public enum State {
        PLANNING_PHASE,
        ACTION_PHASE,
        DEPLOYMENT_PHASE,
        PRODUCTION_PHASE
    }

    public StateManager(Player[] players, GameMap gameMap, List<ConstructedBuilding> buildings,
                        GameScreenController gameScreenController) {
        this.players = players;
        this.gameMap = gameMap;
        this.buildings = buildings;
        this.gameScreenController = gameScreenController;
        playerIdTurnEnded = new HashSet<>();

        gameScreenController.subscribeOnSetState(new Consumer<State>() {
            @Override
            public void accept(State nextState) {
                state = nextState;
            }
        });
    }

    public void endTurnFor(Player player) {
        playerIdTurnEnded.add(player.getId());
        if (playerIdTurnEnded.size() == players.length) {
            gameScreenController.setState(ACTION_PHASE);
        }
    }

    public void update() {
        switch (state) {
            case PLANNING_PHASE:
                break;
            case ACTION_PHASE:
                break;
            case DEPLOYMENT_PHASE:
                break;
            case PRODUCTION_PHASE:
                break;
        }
    }
}
