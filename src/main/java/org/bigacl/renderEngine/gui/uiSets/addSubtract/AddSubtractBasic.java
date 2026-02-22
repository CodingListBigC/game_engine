package org.bigacl.renderEngine.gui.uiSets.addSubtract;

import org.bigacl.renderEngine.gui.fields.Button;
import org.bigacl.renderEngine.gui.fields.Text;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class AddSubtractBasic {
  protected ArrayList<Button> buttonArrayList = new ArrayList<>();
  protected ArrayList<Text> textArrayList = new ArrayList<>();
  protected Vector2f guiPosition;
  protected float guiWidth;
  protected int amountOfRows =0;
  protected float rowSpacing = 10;
  protected float columSpacing = 10;

  /**
   * Render default to all Buttons
   */
  public void render() {
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
   * Render Every Single Text in class
   */
  public void renderAllText(){
    for (Text text: textArrayList){
      text.render();
    }
  }

  public void addButton(Button button) {
    buttonArrayList.add(button);
  }
  public void addText(Text text){
    textArrayList.add(text);
  }

  public void init(){
    this.initText();
    this.initButtons();
  }

  public abstract void initButtons();

  public  abstract void initText();

  public abstract Vector2f getSize();

}
