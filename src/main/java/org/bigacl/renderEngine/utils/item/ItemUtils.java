package org.bigacl.renderEngine.utils.item;

import org.bigacl.renderEngine.designItem.build.BuildItemsAbstract;
import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ItemConst;
import java.util.ArrayList;
import static org.bigacl.renderEngine.utils.consts.ClassConst.itemMangerAbstract;

public class ItemUtils {

  public static void addItemHelper(BasePlaceableItem item) {
    addItemHelper(itemMangerAbstract, item);
  }

  // Fixed: Removed the '&' because BuildItemsAbstract should extend BasePlaceableItem
  public static <T extends BuildItemsAbstract> void addItemHelper(ItemMangerAbstract<T, ?> manager, BuildItemsAbstract item) {
    try {
      manager.addItemAll((T) item);
    } catch (ClassCastException e) {
      System.err.println("Type mismatch: " + item.getClass().getSimpleName());
    }
  }

  public static <T extends BasePlaceableItem> void addItemHelper(ItemMangerAbstract<T, ?> manager, BasePlaceableItem item) {
    try {
      manager.addItemAll((T) item);
    } catch (ClassCastException e) {
      System.err.println("Failed to add: " + item.getClass().getSimpleName());
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends BasePlaceableItem, L extends ItemConst.BasicPlaceableTypes> ArrayList<T> getTypeListHelper(ItemMangerAbstract<T, L> manager, ItemConst.BasicPlaceableTypes type) {
    try {
      return manager.getTypeList((L) type);
    } catch (ClassCastException e) {
      return new ArrayList<>();
    }
  }
}