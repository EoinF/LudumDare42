package com.github.eoinf.screens.main.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.GameMap;
import com.github.eoinf.game.MapTile;
import com.github.eoinf.game.PlacedUnit;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class PlacedUnitActor extends Group implements PlacedObjectActor {
    private static final Color PLACED_COLOUR = new Color(0.4f, 0.2f, 0.5f, 0.8f);

    public PlacedUnitActor(TextureManager textureManager, PlacedUnit placedUnit, GameMap gameMap, Color playerColour) {
        setUserObject(placedUnit);
        setTransform(false); // Avoids triggering a flush of spritebatch on drawing this

        UnitActor unitActor = new UnitActor(textureManager, placedUnit.getUnit(), playerColour);

        if (placedUnit.getDestinationTile() != null) {
            addMoveAction(placedUnit.getOriginTile(), placedUnit.getDestinationTile(), gameMap);
        } else if (placedUnit.getTarget() != null) {
            addMoveAction(placedUnit.getOriginTile(), placedUnit.getTarget(), gameMap);
            ColorAction colourThere = Actions.color(Color.BLACK, 0.6f);
            ColorAction colourBack = Actions.color(Color.WHITE, 1.2f);
            unitActor.addAction(forever(sequence(colourThere, colourBack)));
        }

        if (!placedUnit.isDeployed()) {
            Image background = new Image(textureManager.tiles.blank);
            background.setColor(PLACED_COLOUR);
            addActor(background);
        }
        addActor(unitActor);
    }

    private void addMoveAction(MapTile originTile, MapTile destTile, GameMap gameMap) {
        float originX = originTile.getX() * gameMap.getTileWidth();
        float originY = originTile.getY() * gameMap.getTileHeight();
        float destX = destTile.getX() * gameMap.getTileWidth();
        float destY = destTile.getY() * gameMap.getTileHeight();
        MoveToAction moveThere = Actions.moveTo(destX, destY, 0.8f);
        MoveToAction moveBack = Actions.moveTo(originX, originY);
        DelayAction delay = Actions.delay(0.5f);

        addAction(forever(sequence(moveThere, moveBack, delay)));
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
