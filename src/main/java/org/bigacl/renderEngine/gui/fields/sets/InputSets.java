package org.bigacl.renderEngine.gui.fields.sets;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.joml.Vector2f;

import java.util.ArrayList;

public abstract class InputSets {
  protected ArrayList<InputInterface> itemList = new ArrayList<InputInterface>();
  protected int currentAmount;

  // Format
  protected float spacing = 10;
  protected Vector2f rowSize = new Vector2f(200,10);

  public void renderAll(){
    for (InputInterface item: itemList){
      item.render();
    }
  };
  public void renderOne(int renderNumber){
    if (itemList.size() >= renderNumber){
      itemList.get(renderNumber).render();
    }
  };

  public void viewAmount(int amount){
    checkAmount(amount);
    hideAll();
    for (int itemNumber = 0; itemNumber < amount; itemNumber++) {
      itemList.get(itemNumber).setVisible(true);
    }
  }

  private void hideAll(){
    for (InputInterface item: itemList){
      item.setVisible(false);
    }
  }
  public void checkAmount(int amount){
    while (itemList.size() <= amount){
      newDefaultItem();
    }
  }

  public void addItem(InputInterface inputInterface){
    itemList.add(inputInterface);
  }
  public abstract void newDefaultItem();

  private float getY(int row){
    return row * (spacing + rowSize.y);
  }
  protected float getNextY(){
    return getY(itemList.size());
  }

}
