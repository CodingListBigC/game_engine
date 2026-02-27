package org.bigacl.renderEngine.gui.fields;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

public abstract class InputInterface {
  public abstract void render();

  public void checkInput(Vector2d mouseLocation, int mouseAction) {
    if (!isHovered(mouseLocation))
      return;
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
      leftClick();
    }
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
      rightClick();
    }
  }

  protected abstract void rightClick();

  protected abstract void leftClick();

  public abstract boolean isHovered(Vector2d mouseLocation);

  protected boolean visible = false;

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  ;

  public void toggleVisibility() {
    this.visible = !this.visible;
  }

  ;

  public void setClickedStatus(boolean clicked) {
  }

  public boolean getClickedStatus(){
    return false;
  }
}
