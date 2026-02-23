package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.uiSets.addSubtract.VectorButton;
import org.bigacl.renderEngine.gameItems.item.ItemManger;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class LeftHud extends  ModelDesignAbstractClass{
  float widthPercentage;
  float heightPercentage;
  float panelWidth;
  float panelHeight;

  VectorButton vectorButton;


  public LeftHud() {
    this.widthPercentage = 0.125f;
    this.heightPercentage = 0.75f;

    // This is how wide the actual panel is
    this.panelWidth = screenWidth * widthPercentage;
    this.panelHeight = screenHeight * heightPercentage;

    this.vectorButton = new VectorButton(new Vector2f(0,50), panelWidth);
  }

  @Override
  public void renderMenu() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;


    drawSide(false, nanoVGUI, widthPercentage, heightPercentage);
    this.vectorButton.render();
  }

  @Override
  public void checkHudInputs() {
    ItemManger iM = ClassConst.itemManger;
    if(iM.getHouseList().isEmpty()){
      House addHouse =  new House(new Vector3f(0.0f,0.0f,0.0f));
      iM.addItem(addHouse);
      System.out.println("Add House");
    }
    Vector3f changeLocation = this.vectorButton.checkButtonInput(ClassConst.window.getMouseX(), ClassConst.window.getMouseY(), ClassConst.window.getMouseAction(),iM.getHouseList().get(0).getWorldPosition(),1.0f);
    iM.getHouseList().get(0).setWorldPosition(changeLocation);
  }

  @Override
  public void updateText() {

  }
}
