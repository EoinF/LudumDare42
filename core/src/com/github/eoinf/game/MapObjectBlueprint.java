package com.github.eoinf.game;

import com.badlogic.gdx.math.GridPoint2;

public interface MapObjectBlueprint {
    String getName();
    String getDescription();
    GridPoint2[] getShape();

}
