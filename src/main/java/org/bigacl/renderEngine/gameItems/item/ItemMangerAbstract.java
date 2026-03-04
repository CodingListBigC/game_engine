package org.bigacl.renderEngine.gameItems.item;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Apartment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.utils.consts.ItemConst;

import java.util.ArrayList;

import static org.bigacl.renderEngine.utils.consts.ClassConst.camera;

public abstract class ItemMangerAbstract<renderItem extends BasePlaceableItem, itemType extends ItemConst.BasicPlaceableTypes> {
  protected final ArrayList<renderItem> allItems = new ArrayList<>();

  public void renderAll() {
    renderPlaceableItems();
  }


  void addItemAll(renderItem item) {
    allItems.add(item);
  }

  public ArrayList<? extends BasePlaceableItem> getDefaultData() {
    itemType lastViewIndex = (itemType) HudAbstract.getViewData().getLastViewType();
    itemType viewIndex = (itemType) HudAbstract.getViewData().getViewType();

    ArrayList<? extends BasePlaceableItem> listToShow = getTypeList(viewIndex);

    if (lastViewIndex != viewIndex) {
      HudAbstract.getViewData().setViewDataList(listToShow);
    }
    return listToShow;
  }

  public ArrayList<renderItem> getTypeList(itemType type) {
    Class<?> itemClass = getTypeClass(type);
    if (itemClass == null) {
      return allItems;
    }
    ArrayList<renderItem> returnList = new ArrayList<>();
    for (renderItem item : allItems) {
      if (itemClass.isInstance(item)) {
        returnList.add(item);
      }
    }
    return returnList;
  }

  private Class<?> getTypeClass(itemType type) {
    return switch (type) {
      case APARTMENT -> Apartment.class;
      case HOUSE -> House.class;
      default -> null;
    };
  }


  /**
   * Clean Up Item Manger
   */
  public void cleanupAll() {
    for (renderItem item : allItems) {
      item.cleanup();
    }
  }

  protected abstract void renderAllItems();
}
