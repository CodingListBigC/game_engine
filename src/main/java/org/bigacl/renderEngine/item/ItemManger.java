package org.bigacl.renderEngine.item;

import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.player.BoundingBox;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.bigacl.renderEngine.utils.consts.ClassConst.camera;

public class ItemManger {
  private final ArrayList<House> houseList = new ArrayList<>();

  public void addHouse(House house) {
    house.setupHitbox();
    houseList.add(house);
  }

  // This allows the Camera to loop through houses for collision
  public List<House> getHouseList() {
    return houseList;
  }

  public void renderAll() {
    renderHouse();
  }

  private void renderHouse() {
    for (House house : houseList) {
      BoundingBox box = house.getBoundingBox();
      BoundingBox offSetBox = house.getBoundingBoxOffSet();

      if (box == null) {
        house.render();
        continue;
      }

      if (camera.getFrustum().testAab(offSetBox.minX, offSetBox.minY, offSetBox.minZ, offSetBox.maxX, offSetBox.maxY, offSetBox.maxZ)) {
        // System.out.println("Rendering: " + houseList.indexOf(house));
        house.render();
      }
    }
  }
}
