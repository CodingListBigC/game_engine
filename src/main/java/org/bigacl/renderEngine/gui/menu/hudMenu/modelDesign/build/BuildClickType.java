package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Aparment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.button.tables.TableMaster;
import org.bigacl.renderEngine.utils.ColorSetter;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.awt.*;

import static org.bigacl.renderEngine.utils.consts.ClassConst.itemManger;

public class BuildClickType extends TableMaster<InputInterface>{

  Color btnBackGroundColor = ColorSetter.setNewAlpha(Color.BLUE, 100);
  Color btnTextColor = Color.BLACK;

  public void checkClick() {
    for (InputInterface item : this.getItemArray()) {
      if (item.getClickedStatus())
        return;
    }
  }

  public void initButtons() {
    Button apartmentBtn = new Button("Apartment", btnBackGroundColor, btnTextColor);
    apartmentBtn.setOnClick(() -> {
      this.defaultPlaceMeant(new Aparment());
    });
    this.addItem(apartmentBtn);
    Button houseBtn = new Button("House", btnBackGroundColor, btnTextColor);
    apartmentBtn.setOnClick(() -> {
      this.defaultPlaceMeant(new House());
    });
    this.addItem(houseBtn);

  }

  private void defaultPlaceMeant(BasePlaceableItem defaultItem) {
    // Note: Since we are placing on the floor, Y is usually 0.
    Aparment aparment = new Aparment();
    aparment.setWorldPosition(new Vector3f(0f));
    // 3. Add to manager
    itemManger.addItem(aparment);
  }

}
