package org.bigacl.renderEngine.gui.fields.button.addSubtract;

import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.fields.TextLimits;
import org.bigacl.renderEngine.gui.fields.button.ClickAbstract;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class AddSubtractBasic extends ClickAbstract {
  protected ArrayList<Button> buttonArrayList = new ArrayList<>();
  protected ArrayList<TextLimits> textLimitsArrayList = new ArrayList<>();
  protected Vector2f guiPosition;
  protected float guiWidth;
  protected int amountOfRows = 0;
  protected float rowSpacing = 10;
  protected float columSpacing = 10;

  @Override
  public void render() {
    renderAllButton();
    renderAllText();
  }

  /**
   * Render default to all Buttons
   */
    public void renderAll() {
    renderAllButton();
    renderAllText();
  }

  /**
   * Render Every Button in class
   */
  public void renderAllButton() {
    for (Button button : buttonArrayList) {
      button.render();
    }
  }

  /**
   * Render Every Single TextLimits in class
   */
  public void renderAllText() {
    for (TextLimits textLimits : textLimitsArrayList) {
      textLimits.render();
      textLimits.debugLog();
    }
  }

  public void addButton(Button button) {
    buttonArrayList.add(button);
  }

  public void addText(TextLimits textLimits) {
    textLimitsArrayList.add(textLimits);
  }

  public void init() {
    this.initText();
    this.initButtons();
  }

  public abstract void initButtons();

  public abstract void initText();

  public abstract Vector2f getSize();

}
