package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.PlacedUnit;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class PlacedUnitActor extends Group implements PlacedObjectActor {
    private static final Color PLACED_COLOUR = new Color(0.4f, 0.2f, 0.5f, 0.8f);

    public PlacedUnitActor(TextureManager textureManager, PlacedUnit placedUnit, GameMap gameMap, Color playerColour) {
        setUserObject(placedUnit);
        setTransform(false); // Avoids triggering a flush of spritebatch on drawing this

        if (placedUnit.getDestinationTile() != null) {
            float originX = placedUnit.getOriginTile().getX() * gameMap.getTileWidth();
            float originY = placedUnit.getOriginTile().getY() * gameMap.getTileHeight();
            float destX = placedUnit.getDestinationTile().getX() * gameMap.getTileWidth();
            float destY = placedUnit.getDestinationTile().getY() * gameMap.getTileHeight();
            MoveToAction moveThere = Actions.moveTo(destX, destY, 0.8f);
            MoveToAction moveBack = Actions.moveTo(originX, originY, 0.1f);

            RepeatAction action = forever(sequence(moveThere, moveBack));
            addAction(action);
        }

        UnitActor unitActor = new UnitActor(textureManager, placedUnit.getUnit(), playerColour);

        if (!placedUnit.isDeployed()) {
            Image background = new Image(textureManager.tiles.blank);
            background.setColor(PLACED_COLOUR);
            addActor(background);
        }
        addActor(unitActor);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (super.hit(x, y, touchable) != null) {
            return this;
        } else {
            return null;
        }
    }
}
