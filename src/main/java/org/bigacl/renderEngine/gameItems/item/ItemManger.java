package org.bigacl.renderEngine.gameItems.item;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Apartment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.utils.consts.ItemConst;

import java.util.ArrayList;

import static org.bigacl.renderEngine.utils.consts.ClassConst.camera;

public class ItemManger {
  private final ArrayList<BasePlaceableItem> allPlaceableList = new ArrayList<>();

  public void renderAll() {
    renderPlaceableItems();
  }

  private void renderPlaceableItems() {
    for (BasePlaceableItem item : allPlaceableList) {
      BoundingBox box = item.getBoundingBox();
      BoundingBox offSetBox = item.getBoundingBoxOffSet();

      if (box == null) {
        item.render();
        continue;
      }

      if (camera.getFrustum().testAab(offSetBox.minX, offSetBox.minY, offSetBox.minZ, offSetBox.maxX, offSetBox.maxY, offSetBox.maxZ)) {
        item.render();
      }
    }
  }

  public void addItemAll(BasePlaceableItem item) {
    allPlaceableList.add(item);
  }

  public ArrayList<? extends BasePlaceableItem> getDefaultData() {
    ItemConst.BasicPlaceableTypes lastViewIndex = HudAbstract.getViewData().getLastViewType();
    ItemConst.BasicPlaceableTypes viewIndex = HudAbstract.getViewData().getViewType();

    ArrayList<? extends BasePlaceableItem> listToShow = getTypeList(viewIndex);

    if (lastViewIndex != viewIndex) {
      HudAbstract.getViewData().setViewDataList(listToShow);
    }
    return listToShow;
  }

  public ArrayList<BasePlaceableItem> getTypeList(ItemConst.BasicPlaceableTypes type) {
    Class<?> itemClass = getTypeClass(type);
    if (itemClass == null) {
      return allPlaceableList;
    }
    ArrayList<BasePlaceableItem> returnList = new ArrayList<>();
    for (BasePlaceableItem item : allPlaceableList) {
      if (itemClass.isInstance(item)) {
        returnList.add(item);
      }
    }
    return returnList;
  }

  private Class<?> getTypeClass(ItemConst.BasicPlaceableTypes type) {
    return switch (type) {
      case APARTMENT -> Apartment.class; // Fixed spelling of Apartment
      case HOUSE -> House.class;
      default -> null;
    };
  }

  /**
   * Clean Up Item Manger
   */
  public void cleanupAll() {
    for (BasePlaceableItem item : allPlaceableList) {
      item.cleanup();
    }
  }

}
