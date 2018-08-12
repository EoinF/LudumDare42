package com.github.eoinf.game;

import com.github.eoinf.screens.main.controllers.GameScreenController;

import java.util.List;
import java.util.function.Consumer;

public class AIController {
    private int playerId;
    private Player[] players;
    private List<PlacedBuilding> buildings;
    private boolean isPlanningPhase;
    private boolean isTurnEnded;
    private GameScreenController gameScreenController;

    public AIController(int playerId, Player[] players, List<PlacedBuilding> buildings, StateManager stateManager,
                        GameScreenController gameScreenController) {
        this.players = players;
        this.buildings = buildings;
        this.playerId = playerId;
        this.isPlanningPhase = stateManager.getState() == StateManager.State.PLANNING_PHASE;
        this.gameScreenController = gameScreenController;

        gameScreenController.subscribeOnSetState(new Consumer<StateManager.State>() {
            @Override
            public void accept(StateManager.State state) {
                isPlanningPhase = state == StateManager.State.PLANNING_PHASE;
                isTurnEnded = false;
            }
        });

        gameScreenController.subscribeOnEndTurn(new Consumer<Integer>() {
            @Override
            public void accept(Integer playerTurnEndedId) {
                if (playerId == playerTurnEndedId) {
                    isTurnEnded = true;
                }
            }
        });

    }

    public void update() {
        if (isPlanningPhase && !isTurnEnded) {
            this.gameScreenController.endPlayerTurn(this.playerId);
        }
    }
}
