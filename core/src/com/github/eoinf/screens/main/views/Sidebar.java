package com.github.eoinf.screens.main.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.github.eoinf.TextureManager;
import com.github.eoinf.game.Building;
import com.github.eoinf.screens.main.controllers.GameScreenController;
import com.github.eoinf.screens.main.widgets.BuildingActor;
import com.github.eoinf.screens.main.widgets.BuildingCategory;
import com.github.eoinf.screens.main.widgets.BuildingsTable;
import com.github.eoinf.screens.main.widgets.ProductionTable;
import com.github.eoinf.screens.main.widgets.UnitsTable;
import org.lwjgl.input.Mouse;

import java.util.Map;

public class Sidebar extends BaseView {

    private BuildingsTable buildingsTable;
    private ProductionTable productionTable;
    private UnitsTable unitsTable;

    private Button buildingsTableButton;
    private Button productionTableButton;
    private Button unitsTableButton;

    public Sidebar(int startX, int startY, int width, int height,
                   Batch batch, TextureManager textureManager, GameScreenController gameScreenController) {
        super(startX, startY, width, height, batch, textureManager, Color.FIREBRICK);
        Table sideBarTable = new Table();
        sideBarTable.setFillParent(true);
        sideBarTable.add(createModeSelectionButtons(textureManager))
                .expandX().fillX()
                .top().left();
        sideBarTable.row();
        Table contentsTable = new Table();
        sideBarTable.add(contentsTable)
                .expandX().fillX()
                .left();

        rootTable.add(sideBarTable);

        // A stack for choosing which mode to enter
        Stack modeSelectStack = new Stack();

        buildingsTable = new BuildingsTable(textureManager);
        buildingsTable.top().left().setFillParent(true);

        productionTable = new ProductionTable(textureManager);
        unitsTable = new UnitsTable();

        modeSelectStack.add(unitsTable);
        modeSelectStack.add(productionTable);
        modeSelectStack.add(buildingsTable);

        contentsTable.add(modeSelectStack)
                .expandX()
                .fillX();

        ChangeListener onClickModeButton = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                buildingsTable.setVisible(buildingsTableButton.isChecked());
                productionTable.setVisible(productionTableButton.isChecked());
                unitsTable.setVisible(unitsTableButton.isChecked());

                gameScreenController.setSelectedBuilding(null);
            }
        };

        buildingsTableButton.addListener(onClickModeButton);
        productionTableButton.addListener(onClickModeButton);
        unitsTableButton.addListener(onClickModeButton);

        // Left clicks
        stage.addListener(new ClickListener(Input.Buttons.LEFT) {
                              @Override
                              public void clicked(InputEvent event, float x, float y) {
                                  Actor hit = stage.hit(x, y, true);

                                  if (hit instanceof BuildingActor) {
                                      gameScreenController.setSelectedBuilding((Building) hit.getUserObject());
                                  }
                                  super.clicked(event, x, y);
                              }
                          }
        );

        // Right clicks
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
                              @Override
                              public void clicked(InputEvent event, float x, float y) {
                                  gameScreenController.setSelectedBuilding(null);
                                  super.clicked(event, x, y);
                              }
                          }
        );
    }

    private HorizontalGroup createModeSelectionButtons(TextureManager textureManager) {
        HorizontalGroup modeRow = new HorizontalGroup();

        SpriteDrawable up = new SpriteDrawable(new Sprite(textureManager.ui.iconBuildings));
        SpriteDrawable down = new SpriteDrawable(new Sprite(textureManager.ui.iconBuildings));
        down.getSprite().setColor(Color.YELLOW);
        buildingsTableButton = new Button(new Button.ButtonStyle(up, down, down));
        modeRow.addActor(buildingsTableButton);

        up = new SpriteDrawable(new Sprite(textureManager.ui.iconProduction));
        down = new SpriteDrawable(new Sprite(textureManager.ui.iconProduction));
        down.getSprite().setColor(Color.YELLOW);
        productionTableButton = new Button(new Button.ButtonStyle(up, down, down));
        modeRow.addActor(productionTableButton);

        up = new SpriteDrawable(new Sprite(textureManager.ui.iconUnits));
        down = new SpriteDrawable(new Sprite(textureManager.ui.iconUnits));
        down.getSprite().setColor(Color.YELLOW);
        unitsTableButton = new Button(new Button.ButtonStyle(up, down, down));
        modeRow.addActor(unitsTableButton);

        //Create a button group to only allow one button being pressed at a time
        ButtonGroup<Button> modeSelectGroup =
                new ButtonGroup<>(buildingsTableButton, productionTableButton, unitsTableButton);

        modeSelectGroup.setMaxCheckCount(1);
        modeSelectGroup.setMinCheckCount(1);
        buildingsTableButton.setChecked(true);
        return modeRow;
    }

    public void setBuildingTemplates(Map<BuildingCategory, Building[]> buildingCategoryMap, int tileWidth, int tileHeight) {
        buildingsTable.setBuildings(buildingCategoryMap, tileWidth, tileHeight);
    }
}
