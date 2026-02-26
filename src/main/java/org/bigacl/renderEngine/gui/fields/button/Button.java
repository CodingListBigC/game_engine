package org.bigacl.renderEngine.gui.fields.button;

import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Button extends ClickAbstract{
  private final String code;

  /**
   * Create Button for Hud
   *
   * @param size            Button Size (x=width, y=height)
   * @param textColor       Button TextLimits Color (x=red, y=green, z=blue)
   */
  public Button(String label,String code, Vector2f size, Vector2f location, Vector4f backgroundColor, Vector3f textColor) {
    this.text = new Text(label,0);
    this.code = code;
    this.size = size;
    this.location = location;
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
  }
  public Button(String label,String code, Vector2f size, Vector4f backgroundColor, Vector3f textColor) {
    this.text = new Text(label,0);
    this.code = code;
    this.size = size;
    this.location = new Vector2f();
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;
  }

  public void setBackgroundColor(Vector4f backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setTextColor(Vector3f textColor) {
    this.textColor = textColor;
  }

  public String getCode() {
    return code;
  }

  @Override
  protected void leftClick() {
  }

  @Override
  protected void rightClick() {

  }

}
