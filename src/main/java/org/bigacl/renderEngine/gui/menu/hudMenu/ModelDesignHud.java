package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.uiSets.addSubtract.ModelButton;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ModelDesignHud extends HudAbstract {

  Vector4f mainBackgroundColor = new Vector4f(0.5f, 0.5f, 0.5f, 0.25f);
  ModelButton modelButton;
  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;
  float screenWidth;
  float screenHeight;

  public ModelDesignHud() {
    debugMenu = new DebugMenu();
    this.widthPercentage = 0.25f;
    this.heightPercentage = 0.75f;

    this.screenWidth = ClassConst.window.getWidth();
    this.screenHeight = ClassConst.window.getHeight();

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;

    this.modelButton = new ModelButton(new Vector2f(0.0f,0.0f), panelWidth);
  }


  @Override
  public void renderAll() {
    leftMenu();
    //rightMenu();
  }

  public void leftMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;


    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);
    this.modelButton.renderAllButton();

  }

  public void rightMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    float widthPercentage = 0.25f;
    float heightPercentage = 0.75f;

    float screenWidth = ClassConst.window.getWidth();
    float screenHeight = ClassConst.window.getHeight();

    // This is how wide the actual panel is
    float panelWidth = screenWidth * widthPercentage;
    float panelHeight = screenHeight * heightPercentage;

    drawSide(true, nanoVGUI,widthPercentage, heightPercentage);

  }

  public void drawSide(boolean isRightSide, NanoVGUI nanoVGUI, float widthPercentage, float heightPercentage) {
    float screenWidth = ClassConst.window.getWidth();
    float screenHeight = ClassConst.window.getHeight();

    // This is how wide the actual panel is
    float panelWidth = screenWidth * widthPercentage;
    float panelHeight = screenHeight * heightPercentage;
    float startX = 0;

    if (isRightSide) {
      // Move the starting point to the far right, minus the panel width
      startX = screenWidth - panelWidth;
    }

    // Position: (startX, 0)
    // Size: (panelWidth, screenHeight)
    Vector2f pos = new Vector2f(startX, 0.0f);
    Vector2f size = new Vector2f(panelWidth, panelHeight);

    nanoVGUI.drawRect(pos, size, mainBackgroundColor);
  }

  public void checkHudInputs(){
    checkModelInputs();;
  }

  private void checkModelInputs(){
    ItemManger iM = ClassConst.itemManger;
    if(iM.getHouseList().isEmpty()){
      House addHouse =  new House(new Vector3f(0.0f,0.0f,0.0f));
      iM.addItem(addHouse);
      System.out.println("Add House");
    }
    this.modelButton.checkButtonInput(ClassConst.window.getMouseX(), ClassConst.window.getMouseY(), ClassConst.window.getMouseAction(),iM.getHouseList().get(0),1.0f);
  }
}
