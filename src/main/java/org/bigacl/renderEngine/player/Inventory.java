package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.gameItems.item.ItemMangerMainGame;

import java.util.ArrayList;

public class Inventory {
  private ArrayList<ItemMangerMainGame> itemList;
  private int[] slotItems = new int[9];

  public ItemMangerMainGame[] getSlotsItems() {
    ItemMangerMainGame[] returnItems = new ItemMangerMainGame[slotItems.length];
    for (int slot = 0; slot < slotItems.length; slot++) {
      returnItems[slot] = itemList.get(slot);
    }
    return returnItems;
  }

  public ArrayList<ItemMangerMainGame> getItemList() {
    return itemList;
  }

  public int[] getSlotItems() {
    return slotItems;
  }

  public void addItem(ItemMangerMainGame item) {
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
