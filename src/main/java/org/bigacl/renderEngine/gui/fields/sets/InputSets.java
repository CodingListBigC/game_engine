package org.bigacl.renderEngine.gui.fields.sets;

import org.bigacl.renderEngine.gui.fields.InputInterface;

import java.util.ArrayList;

public abstract class InputSets {
  protected ArrayList<InputInterface> itemList = new ArrayList<InputInterface>();
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

}
