package org.bigacl.renderEngine.gameItems.item;

public interface ItemInterface {
  void init();
  void leftClick();
  void rightClick();
  void render();
  void cleanup();
}
