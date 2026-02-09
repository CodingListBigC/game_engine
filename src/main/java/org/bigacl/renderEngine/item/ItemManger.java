package org.bigacl.renderEngine.item;

import org.bigacl.renderEngine.item.placeable.aparment.Aparment;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.player.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static org.bigacl.renderEngine.utils.consts.ClassConst.camera;

public class ItemManger {
  private final ArrayList<House> houseList = new ArrayList<>();
  private final ArrayList<Aparment> apartmentList = new ArrayList<Aparment>();


  public void renderAll() {
    renderHouse();
    renderApartment();
  }

  /**
   * Add item to manger for house
   * @param house - House Item
   */
  public void addItem(@NotNull House house) {
    house.setupHitbox();
    houseList.add(house);
  }
  /**
   * Add item to manger for aparment.
   * @param apartment - Apartment Item
   */
  public void addItem(@NotNull Aparment apartment) {
    apartment.setupHitbox();
    apartmentList.add(apartment);
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
  private void renderApartment() {
    for (Aparment aparment : apartmentList) {
      BoundingBox box = aparment.getBoundingBox();
      BoundingBox offSetBox = aparment.getBoundingBoxOffSet();

      if (box == null) {
        aparment.render();
        continue;
      }

      if (camera.getFrustum().testAab(offSetBox.minX, offSetBox.minY, offSetBox.minZ, offSetBox.maxX, offSetBox.maxY, offSetBox.maxZ)) {
        System.out.println("Rendering apartment: " + apartmentList.indexOf(aparment));
        offSetBox.log("Rendering apartment: ");
        aparment.render();
      }
    }
  }

  public List<House> getHouseList() {
    return houseList;
  }
  public ArrayList<Aparment> getApartmentList() {
    return apartmentList;
  }
}
