package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.gameItems.item.ItemManger;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<ItemManger> itemList;
  private int[] slotItems = new int[9];

  public ItemManger[] getSlotsItems() {
    ItemManger[] returnItems = new ItemManger[slotItems.length];
    for (int slot = 0; slot < slotItems.length; slot++) {
      returnItems[slot] = itemList.get(slot);
    }
    return returnItems;
  }

  public ArrayList<ItemManger> getItemList() {
    return itemList;
  }

  public int[] getSlotItems() {
    return slotItems;
  }

  public void addItem(ItemManger item) {
    int addToSlotNumber = checkAdaviableSlots();
    itemList.add(item);
    if (addToSlotNumber != 0) {
      slotItems[addToSlotNumber] = itemList.indexOf(item);
    }
  }

  private int checkAdaviableSlots() {
    for (int slotNumber = 0; slotNumber < slotItems.length; slotNumber++) {
      if (slotItems[slotNumber] == 0) {
        return slotNumber;
      }
    }
    return 0;
  }
}
