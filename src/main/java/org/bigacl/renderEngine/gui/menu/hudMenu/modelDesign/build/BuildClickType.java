package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Aparment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.button.tables.TableMaster;
import org.bigacl.renderEngine.utils.ColorSetter;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.awt.*;

import static org.bigacl.renderEngine.utils.consts.ClassConst.itemManger;

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
      Aparment newAparment = new Aparment();
      System.out.println("Item class: " + newAparment.getClass());
      this.defaultPlaceMeant(newAparment);
    });
    this.addItem(apartmentBtn);
    Button houseBtn = new Button("House", btnBackGroundColor, btnTextColor);
    houseBtn.setOnClick(() -> {
      House newItem = new House();
      this.defaultPlaceMeant(newItem);
    });
    this.addItem(houseBtn);

    this.setSizes();
  }

  private void defaultPlaceMeant(BasePlaceableItem defaultItem) {
    // Note: Since we are placing on the floor, Y is usually 0.
    defaultItem.place(new Vector3f(0), 0);
    itemManger.addItemAll(defaultItem);
  }

}
