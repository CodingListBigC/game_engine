package org.bigacl.renderEngine.utils.item;

import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ItemConst;

import java.util.ArrayList;

import static org.bigacl.renderEngine.utils.consts.ClassConst.itemMangerAbstract;

public class ItemUtils {
  // 2. The Helper Method that "Captures" the wildcard
  public static void addItemHelper(BasePlaceableItem item) {
    // Inside here, 'T' is a known name, so Java allows the operation.
    // We cast the item to 'T' because we are taking responsibility for the type safety.
    addItemHelper(itemMangerAbstract, item);
  }

  public static <T extends BasePlaceableItem> void addItemHelper(ItemMangerAbstract<T, ?> manager, BasePlaceableItem item) {
    // Inside here, 'T' is a known name, so Java allows the operation.
    // We cast the item to 'T' because we are taking responsibility for the type safety.
    manager.addItemAll((T) item);
  }

  public static <T extends BasePlaceableItem, L extends ItemConst.BasicPlaceableTypes> ArrayList<T> getTypeListHelper(ItemMangerAbstract<T, L> manger, ItemConst.BasicPlaceableTypes type) {
    // We cast 'type' to 'L' to satisfy the manager's specific enum requirement
    // We return ArrayList<T> because the manager returns the items, not the enum constants
    return manger.getTypeList((L) type);
  }
}
