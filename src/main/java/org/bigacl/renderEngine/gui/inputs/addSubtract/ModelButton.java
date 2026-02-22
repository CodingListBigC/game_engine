package org.bigacl.renderEngine.gui.inputs.addSubtract;

import org.bigacl.renderEngine.gui.inputs.Button;
import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ModelButton extends AddSubtractBasic {

  public ModelButton(Vector2f guiPosition, float width) {
    this.guiPosition = guiPosition;
    this.guiWidth = width;
    initButtons();
  }

  @Override
  public void initButtons() {
    Vector2f buttonSize = new Vector2f(20, 20);
    Vector4f buttonBackgroundColor = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
    Vector3f buttonTextColor = new Vector3f(1, 1, 1);
    Button addX = new Button("+", "100", buttonSize,getButtonPos(buttonSize,false,amountOfRows), buttonBackgroundColor, buttonTextColor);
    Button subX = new Button("-", "200", buttonSize,getButtonPos(buttonSize,true,amountOfRows), buttonBackgroundColor, buttonTextColor);
    this.addButton(addX);
    this.addButton(subX);
    this.amountOfRows++;
    Button addY = new Button("+", "010", buttonSize,getButtonPos(buttonSize,false,amountOfRows), buttonBackgroundColor, buttonTextColor);
    Button subY = new Button("-", "020", buttonSize,getButtonPos(buttonSize,true,amountOfRows), buttonBackgroundColor, buttonTextColor);
    this.addButton(addY);
    this.addButton(subY);
    this.amountOfRows++;
    Button addZ = new Button("+", "001", buttonSize,getButtonPos(buttonSize,false,amountOfRows), buttonBackgroundColor, buttonTextColor);
    Button subZ = new Button("-", "002", buttonSize,getButtonPos(buttonSize,true,amountOfRows), buttonBackgroundColor, buttonTextColor);
    this.addButton(addZ);
    this.addButton(subZ);
  }

  private Vector2f getButtonPos(Vector2f size, boolean side, int row) {

    float x = side ? (guiWidth - size.x) - guiPosition.x : guiPosition.x;
    float y = (float) (guiPosition.y + ((row * 1.5) * size.y));

    return new Vector2f(x, y);

  }
  public void checkButtonInput(double mouseX, double mouseY, int action, BasePlaceableItem item, float snap) {
    // 1. Only trigger on the initial PRESS
    if (action != 1) return;

    for (Button button : buttonArrayList) {
      if (!button.isHovered(mouseX, mouseY)) continue;

      char[] chars = button.getCode().toLowerCase().toCharArray();
      if (chars.length < 3) continue;

      // Use a single 'changePosition' call to avoid logic overlap
      float dx = 0, dy = 0, dz = 0;

      if (chars[0] == '1') dx = snap;
      else if (chars[0] == '2') dx = -snap;

      if (chars[1] == '1') dy = snap;
      else if (chars[1] == '2') dy = -snap;

      if (chars[2] == '1') dz = snap;
      else if (chars[2] == '2') dz = -snap;

      item.changePosition(dx, dy, dz);

      // 2. IMPORTANT: Consume the click so it doesn't fire again next frame
      // You need to set your global mouse action variable to -1 here
      ClassConst.window.setMouseAction(-1);
    }
  }
}
