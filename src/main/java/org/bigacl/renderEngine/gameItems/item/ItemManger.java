package org.bigacl.renderEngine.gameItems.item;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.utils.consts.ItemConst;

import static org.bigacl.renderEngine.utils.consts.ClassConst.camera;

public class ItemManger extends ItemMangerAbstract<BasePlaceableItem, ItemConst.BasicPlaceableTypes> {

  @Override
  protected void renderAllItems() {
    for (BasePlaceableItem item : allItems) {
      item.checkRenderCamera();
    }
  }
}
