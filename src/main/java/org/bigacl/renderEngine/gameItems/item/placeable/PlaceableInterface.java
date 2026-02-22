package org.bigacl.renderEngine.gameItems.item.placeable;

import org.bigacl.renderEngine.gameItems.item.ItemInterface;
import org.joml.Vector3f;

public interface PlaceableInterface extends ItemInterface {
  void place(Vector3f positon, float rotation); // Place Item;
  boolean checkPlace(Vector3f positon, float rotation); // Will return false if can not place at position.

}
