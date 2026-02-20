package org.bigacl.renderEngine.gui.inputs.addSubtract;

import org.bigacl.renderEngine.gui.inputs.Button;
import org.bigacl.renderEngine.gui.inputs.ButtonsInterface;

import java.util.ArrayList;

public abstract class AddSubtractBasic implements ButtonsInterface {
  protected ArrayList<Button> buttonArrayList = new ArrayList<>();

  /** Render Every Button in class */
  public void renderAllButton(){
    for (Button button: buttonArrayList){
      button.renderButton();
    }
  }

  public void addButton(Button button){
    buttonArrayList.add(button);
  }

  public abstract void initButtons();
}
