package org.bigacl.renderEngine.gui.fields.button;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public abstract class ClickAbstract implements InputInterface {
  protected Vector2f size;
  protected Vector2f location;
  protected String label;
  protected Text text;
  /**
   * The color of the button background (x=R, y=G, z=B, w=A)
   */
  protected Vector4f backgroundColor;
  /**
   * The color of the button text (x=R, y=G, z=B)
   */
  Vector3f textColor;
  protected Vector4f outlineColor;


  public void checkInput(Vector2d mouseLocation, int mouseAction){
    if (!isHovered(mouseLocation))
      return;
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT){
      leftClick();
    }
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
      rightClick();
    }
  };

  public boolean checkClickable(Vector2d mouseLocation, int mouseAction){
    return mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT && isHovered(mouseLocation);
  };

  public boolean isHovered(Vector2d mouseLocation) {
    Vector2f startPosition = location;
    Vector2f endPosition = location.add(size);
    return mouseLocation.x >= startPosition.x && mouseLocation.x <= endPosition.x &&
            mouseLocation.y >= startPosition.y && mouseLocation.y <= endPosition.y;
  }

  abstract protected void leftClick();
  abstract protected void rightClick();

  public void render() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawRect(location, size, backgroundColor);
    text.render(location,size,textColor);
  }

  public void changeText(String changeTo){
    text.setText(changeTo);
  }

  public void setLocation(Vector2f location) {
    this.location = location;
  }
}

