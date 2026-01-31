package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.item.ItemManger;
import org.joml.Vector3f;

public class Player {
  private final String username;
  private final String name;
  private final Vector3f color;
  private final Inventory inventory;
  private int money = 1000;

  public Player(String username, String name, Vector3f color) {
    this.username = username;
    this.name = name;
    this.color = color;
    this.inventory = new Inventory();
  }
  public void addItem(ItemManger item){
    inventory.addItem(item);
  };

  public boolean canBuyItem(int amount){
    if (amount > money){
      return false;
    }
    return true;
  }
  public boolean buyItem(int amount){
    if (!canBuyItem(amount)){
      return false;
    }
    money -= amount;
    return true;
  }
}
