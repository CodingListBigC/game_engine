package org.bigacl.renderEngine.gameItems.item;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.utils.consts.ItemConst;

public class ItemMangerMainGame extends ItemMangerAbstract<BasePlaceableItem, ItemConst.BasicPlaceableTypes> {

  @Override
  protected void renderAllItems() {
    for (BasePlaceableItem item : allItems) {
      item.checkRenderCamera();
    }
  }
}
