package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedUnit;
import com.github.eoinf.game.Unit;
import com.github.eoinf.screens.main.controllers.GameScreenController;

public class SelectedPlacedUnitActionsGroup extends Group {

    public SelectedPlacedUnitActionsGroup(PlacedUnit placedUnit, TextureManager textureManager,
                                          GameScreenController gameScreenController, GameMap gameMap) {
        setTransform(false);
        setTouchable(Touchable.childrenOnly);
        Group actionsGroup = new Group();
        actionsGroup.setTouchable(Touchable.disabled);
        actionsGroup.setPosition(placedUnit.getOriginTile().getX() * gameMap.getTileWidth(),
                placedUnit.getOriginTile().getY() * gameMap.getTileHeight() - 32);

        addActor(actionsGroup);

        Group reference = this;

        if (placedUnit.isDeployed()) {
            ActionButton moveButton = new ActionButton("Move", textureManager.skin) {
                @Override
                public void onClick() {
                    MovementTileGroup movementTileGroup = new MovementTileGroup(placedUnit, textureManager, gameMap,
                            gameScreenController);
                    reference.addActor(movementTileGroup);
                    actionsGroup.clearChildren();
                }
            };
            actionsGroup.addActor(moveButton);

            if (placedUnit.getUnit().getWeapon() != Unit.WeaponType.NONE) {
                ActionButton attackButton = new ActionButton("Attack", textureManager.skin) {
                    @Override
                    public void onClick() {
                        AttackTileGroup attackTileGroup = new AttackTileGroup(placedUnit, textureManager, gameMap,
                                gameScreenController);
                        reference.addActor(attackTileGroup);
                        actionsGroup.clearChildren();
                    }
                };

                attackButton.setPosition(64, 0);
                actionsGroup.addActor(attackButton);
            }

            if (placedUnit.getOriginTile().getBuilding() != null &&
                    placedUnit.getOriginTile().getBuilding().getOwner() != placedUnit.getOwner()) {
                ActionButton attackButton = new ActionButton("Raze", textureManager.skin) {
                    @Override
                    public void onClick() {
                        placedUnit.setRazeTarget(placedUnit.getOriginTile().getBuilding());
                        actionsGroup.clearChildren();
                    }
                };

                attackButton.setPosition(64, 0);
                actionsGroup.addActor(attackButton);
            }
        }
    }
}
