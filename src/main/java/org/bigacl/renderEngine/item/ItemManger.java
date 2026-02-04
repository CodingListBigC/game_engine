package org.bigacl.renderEngine.item;

import org.bigacl.renderEngine.item.placeable.house.House;
import java.util.ArrayList;
import java.util.List;

public class ItemManger {
  private final ArrayList<House> houseList = new ArrayList<>();

  public void addHouse(House house){
    house.setupHitbox();
    houseList.add(house);
  }

  // This allows the Camera to loop through houses for collision
  public List<House> getHouseList() {
    return houseList;
  }

  public void renderAll(){
    renderHouse();
  }

  private void renderHouse(){
    for (House house : houseList){
      if (house != null){
        house.render();
      }
    }
  }
}
