package com.github.eoinf.game;

import com.github.eoinf.screens.main.controllers.GameScreenController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.github.eoinf.game.StateManager.State.ACTION_PHASE;
import static com.github.eoinf.game.StateManager.State.DEPLOYMENT_PHASE;
import static com.github.eoinf.game.StateManager.State.PLANNING_PHASE;
import static com.github.eoinf.game.StateManager.State.PRODUCTION_PHASE;

public class StateManager {
    private GameMap gameMap;
    private Player[] players;
    private List<PlacedBuilding> buildings;


    private Set<Integer> playerIdTurnEnded;
    private GameScreenController gameScreenController;
    private State state;

    public GameMap getMap() {
        return gameMap;
    }

    public Player[] getPlayers() {
        return players;
    }

    public State getState() {
        return this.state;
    }

    public List<PlacedBuilding> getBuildings() {
        return buildings;
    }


    public enum State {
        PLANNING_PHASE,
        ACTION_PHASE,
        DEPLOYMENT_PHASE,
        PRODUCTION_PHASE
    }

    public StateManager(Player[] players, GameMap gameMap, List<PlacedBuilding> buildings,
                        GameScreenController gameScreenController) {
        this.players = players;
        this.gameMap = gameMap;
        this.buildings = buildings;
        this.gameScreenController = gameScreenController;
        this.state = PLANNING_PHASE;
        playerIdTurnEnded = new HashSet<>();

        gameScreenController.subscribeOnSetState(new Consumer<State>() {
            @Override
            public void accept(State nextState) {
                if (state != nextState) {
                    if (state == PLANNING_PHASE) {
                        // Leaving the planning phase (All players hit end turn)
                        playerIdTurnEnded.clear();
                    } else if (nextState == PLANNING_PHASE) {
                        // Entering the planning phase
                        for (Player player: players) {
                            player.calculateNextResources(buildings);
                            gameScreenController.changePlayer(player);
                        }
                    }
                }
                state = nextState;
            }
        });

        gameScreenController.subscribeOnEndTurn(new Consumer<Integer>() {
            @Override
            public void accept(Integer playerId) {
                endTurnFor(playerId);
            }
        });
    }

    public void endTurnFor(int playerId) {
        playerIdTurnEnded.add(playerId);
    }

    public void update() {
        switch (state) {
            case PLANNING_PHASE:
                if (playerIdTurnEnded.size() == players.length) {
                    gameScreenController.setState(ACTION_PHASE);
                }
                break;
            case ACTION_PHASE:
                gameScreenController.setState(DEPLOYMENT_PHASE);
                break;
            case DEPLOYMENT_PHASE:
                gameScreenController.setState(PRODUCTION_PHASE);
                break;
            case PRODUCTION_PHASE:
                productionPhase();
                gameScreenController.setState(PLANNING_PHASE);
                break;
        }
    }

    private void productionPhase() {
        for (Player player : this.players) {
            player.resetResourceUsage();

            for (PlacedBuilding building : buildings) {
                if (building.isConstructed()) {
                    if (player.getId() == building.getOwner()) {
                        building.getBuilding().getEffect().applyTo(player);
                    }
                } else {
                    building.setIsConstructed(true);
                    gameScreenController.changeBuilding(building);
                }
            }
            player.collectNewResources();

            gameScreenController.changePlayer(player);
        }
    }
}
