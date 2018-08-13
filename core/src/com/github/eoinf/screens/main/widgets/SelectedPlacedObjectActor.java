package com.github.eoinf.screens.main.widgets;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedBuilding;
import com.github.eoinf.game.PlacedObject;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.game.Player;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class SelectedPlacedObjectActor extends Group {
    private TextureManager textureManager;
    private Player humanPlayer;
    private GameScreenController gameScreenController;

    public SelectedPlacedObjectActor(TextureManager textureManager, Player humanPlayer,
                                     GameScreenController gameScreenController) {
        this.textureManager = textureManager;
        this.humanPlayer = humanPlayer;
        this.gameScreenController = gameScreenController;
        setTransform(false);
    }

    public void setPlacedObject(PlacedObject object, GameMap gameMap) {
        this.setUserObject(object);
        this.clear();
        if (object != null) {
            if (object instanceof PlacedBuilding) {
                SelectedPlacedBuildingActionsGroup group = new SelectedPlacedBuildingActionsGroup((PlacedBuilding) object,
                        textureManager, gameScreenController, gameMap);
                addActor(group);
            }

            if (object instanceof PlacedUnit) {
                SelectedPlacedUnitActionsGroup group = new SelectedPlacedUnitActionsGroup((PlacedUnit) object,
                        textureManager, gameScreenController, gameMap);
                addActor(group);

            }
        }
    }

    public void clearPlacedObject() {
        this.setUserObject(null);
        this.clear();
    }
}
