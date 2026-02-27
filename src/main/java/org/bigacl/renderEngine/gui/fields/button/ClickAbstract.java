package org.bigacl.renderEngine.gui.fields.button;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public abstract class ClickAbstract extends InputInterface {
  protected Vector2f size;
  protected Vector2f location;
  protected String label;
  protected Text text;
  /**
   * The color of the button background (x=R, y=G, z=B, w=A)
   */
  protected Color backgroundColor;
  /**
   * The color of the button text (x=R, y=G, z=B)
   */
  Color textColor;
  protected Color outlineColor;


  public boolean checkClickable(Vector2d mouseLocation, int mouseAction){
    return mouseAction == GLFW.GLFW_MOUSE_BUTTON_LEFT && isHovered(mouseLocation);
  };

  public boolean isHovered(Vector2d mouseLocation) {
    Vector2f startPosition = location;
    Vector2f endPosition = new Vector2f(startPosition).add(size);
    return mouseLocation.x >= startPosition.x && mouseLocation.x <= endPosition.x &&
            mouseLocation.y >= startPosition.y && mouseLocation.y <= endPosition.y;
  }

  public void render() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    System.out.print(location);
    nanoVGUI.drawRect(location, size, backgroundColor);
    text.renderLimits(location,size,textColor);
  }

  public void changeText(String changeTo){
    text.setText(changeTo);
  }

  public void setLocation(Vector2f location) {
    this.location = location;
  }
}

