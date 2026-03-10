package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build;

import org.bigacl.renderEngine.designItem.BuildItemsAbstract;
import org.bigacl.renderEngine.designItem.building.Stairs;
import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Apartment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
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
    Button apartmentBtn = new Button("Apartment", btnBackGroundColor, btnTextColor);
    apartmentBtn.setOnClick(() -> {
      Stairs newStair = new Stairs();
      System.out.println("Item class: " + newStair.getClass());
      this.defaultPlaceMeant(newStair);
    });
    this.addItem(apartmentBtn);

    this.setSizes();
  }

  private void defaultPlaceMeant(BuildItemsAbstract defaultItem) {
    // Note: Since we are placing on the floor, Y is usually 0.
    // defaultItem.place(new Vector3f(0), 0);
    //addItemHelper(itemMangerAbstract, defaultItem);
  }


}
