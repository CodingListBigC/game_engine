package org.bigacl.renderEngine.gui.fields.button;

import org.bigacl.renderEngine.gui.fields.Text;
import org.joml.Vector2f;

import java.awt.*;

public class Button extends ClickAbstract{
  boolean inputVisible = false;
  @Override
  public void rightClick() {

  }

  @Override
  public void leftClick() {

  }

  @Override
  public boolean isInputVisible() {
    return inputVisible;
  }

  private final String code;

  /**
   * Create Button for Hud
   *
   * @param size            Button Size (x=width, y=height)
   * @param textColor       Button TextLimits Color (x=red, y=green, z=blue)
   */
  public Button(String label, String code, Vector2f size, Vector2f location, Color backgroundColor, Color textColor) {
    this.text = new Text(label,0);
    this.code = code;
    this.size = size;
    this.location = location;
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
  }
  public Button(String label,String code, Vector2f size, Color backgroundColor, Color textColor) {
    this.text = new Text(label,0);
    this.code = code;
    this.size = size;
    this.location = new Vector2f();
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setTextColor(Color textColor) {
    this.textColor = textColor;
  }

  public String getCode() {
    return code;
  }

}
