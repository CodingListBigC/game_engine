package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.gameItems.item.ItemManger;
import org.bigacl.renderEngine.player.level.Level;
import org.joml.Vector3f;

public class Player {
  private final String username;
  private final String name;
  private final Vector3f color;
  private final Inventory inventory = new Inventory();
  private int money = 1000;
  private final Level level = new Level();

  public Player(String username, String name, Vector3f color) {
    this.username = username;
    this.name = name;
    this.color = color;
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

  public int getCurrentLevel() {
    return level.getCurrentLevel();
  }
  public int getAmountOfExperience(){
    return level.getAmountOfExperience();
  }
  public void addExperience(int experience){
    level.addAmountOfExperience(experience);
  }

  public PlayerStats getPlayerStats(){
    return new PlayerStats(this.username, this.color, this.money, this.level);
  }
}
