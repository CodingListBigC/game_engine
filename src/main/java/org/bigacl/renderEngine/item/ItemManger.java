package org.bigacl.renderEngine.item;

import org.bigacl.renderEngine.item.placeable.house.House;

import java.util.ArrayList;

public class ItemManger {
  private ArrayList<House> houseList = new ArrayList<>();
  public void addHouse(House house){
    houseList.add(house);
  }
  public void renderAll(){
    renderHouse();
  }

  private void renderHouse(){
    for (House house: houseList){
      if (house != null){
        house.render();
      }
    }
  }
}
