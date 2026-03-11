package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build;

import org.bigacl.renderEngine.designItem.BuildItemsAbstract;
import org.bigacl.renderEngine.designItem.building.Block;
import org.bigacl.renderEngine.designItem.building.Stairs;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.button.tables.TableMaster;
import org.bigacl.renderEngine.utils.ColorSetter;
import org.joml.Vector2d;
import org.joml.Vector3f;

import java.awt.*;

import static org.bigacl.renderEngine.utils.consts.ClassConst.itemMangerAbstract;
import static org.bigacl.renderEngine.utils.item.ItemUtils.addItemHelper;

public class BuildClickType extends TableMaster<Button> {

  Color btnBackGroundColor = ColorSetter.setNewAlpha(Color.BLUE, 100);
  Color btnTextColor = Color.BLACK;

  public BuildClickType() {
    initButtons();
  }

  public void checkClick(Vector2d mouseLocation) {

    for (Button item : this.getItemArray()) {
      if (item.isHovered(mouseLocation)) {
        item.onClick();
        return;

      }
    }
  }

  public void initButtons() {
    createButton("Stairs 0", () -> this.defaultPlaceMeant(new Stairs(0)));
    createButton("Stairs 1", () -> this.defaultPlaceMeant(new Stairs(1)));
    createButton("Stairs 2", () -> this.defaultPlaceMeant(new Stairs(2)));
    createButton("Stairs 3", () -> this.defaultPlaceMeant(new Stairs(3)));
    createButton("Stairs 4", () -> this.defaultPlaceMeant(new Stairs(4)));
    createButton("Whole Block", () -> this.defaultPlaceMeant(new Block(0)));
    createButton("Half Block 0", () -> this.defaultPlaceMeant(new Block(1)));
    createButton("Half Block 1", () -> this.defaultPlaceMeant(new Block(2)));
    createButton("Eight Block", () -> this.defaultPlaceMeant(new Block(3)));


    this.setSizes();
  }

  public void createButton(String name, Runnable run) {
    Button newButton = new Button(name, btnBackGroundColor, btnTextColor);
    newButton.setOnClick(run);
    this.addItem(newButton);
  }

  private void defaultPlaceMeant(BuildItemsAbstract defaultItem) {
    // Note: Since we are placing on the floor, Y is usually 0.
    defaultItem.place(new Vector3f(0), 0);
    addItemHelper(itemMangerAbstract, defaultItem);
  }


}
