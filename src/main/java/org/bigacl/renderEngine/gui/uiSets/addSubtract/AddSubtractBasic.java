package org.bigacl.renderEngine.gui.uiSets.addSubtract;

import org.bigacl.renderEngine.gui.fields.Button;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class AddSubtractBasic {
  protected ArrayList<Button> buttonArrayList = new ArrayList<>();
  protected Vector2f guiPosition;
  protected float guiWidth;
  protected int amountOfRows =0;
  protected float rowSpacing = 10;
  /**
   * Render Every Button in class
   */
  public void renderAllButton() {
    for (Button button : buttonArrayList) {
      button.renderButton();
    }
  }

  /**
   * Render default to all Buttons
   */
  public void render() {
    renderAllButton();
  }

  public void addButton(Button button) {
    buttonArrayList.add(button);
  }

  public abstract void initButtons();

  public abstract Vector2f getSize();

}
