package org.bigacl.renderEngine.gui.fields;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

public interface InputInterface {
  void render();
  default void checkInput(Vector2d mouseLocation, int mouseAction){
    if (!isHovered(mouseLocation))
      return;
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT){
      leftClick();
    }
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
      rightClick();
    }
  }

  void rightClick();

  void leftClick();

  boolean isHovered(Vector2d mouseLocation);

  boolean isInputVisible();
  default  void setInputVisible(boolean visible){
    if (isInputVisible() != visible){
      toggleVisibility();
    }
  };

  default void toggleVisibility(){
    setInputVisible(!isInputVisible());
  }
}
