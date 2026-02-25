package org.bigacl.renderEngine.gui.fields.sets;

import org.bigacl.renderEngine.gui.fields.InputAbstract;

import java.util.ArrayList;

public abstract class InputSets {
  protected ArrayList<InputAbstract> itemList = new ArrayList<InputAbstract>();
  public void renderAll(){
    for (InputAbstract item: itemList){
      item.render();
    }
  };
  public void renderOne(int renderNumber){
    if (itemList.size() >= renderNumber){
      itemList.get(renderNumber).render();
    }
  };

}
