package org.bigacl.renderEngine.gui.inputs;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Button {
  private final String code;
  String label;
  /**
   * Size = (x=width, y=height)
   */
  Vector2f size;
  Vector2f startPosition;
  Vector2f endPosition;
  /**
   * The color of the button background (x=R, y=G, z=B, w=A)
   */
  Vector4f backgroundColor;
  /**
   * The color of the button text (x=R, y=G, z=B)
   */
  Vector3f textColor;

  /**
   * Create Button for Hud
   *
   * @param label           Button Label
   * @param size            Button Size (x=width, y=height)
   * @param startPosition   Button Start Position (x, y)
   * @param backgroundColor Button Background Color (x=red, y=green, z=blue, w=alpha)
   * @param textColor       Button Text Color (x=red, y=green, z=blue)
   */
  public Button(String label,String code, Vector2f size, Vector2f startPosition, Vector4f backgroundColor, Vector3f textColor) {
    this.label = label;
    this.code = code;
    this.size = size;
    this.startPosition = startPosition;
    this.endPosition = new Vector2f(startPosition.x + size.x, startPosition.y + size.y);
    this.backgroundColor = backgroundColor;
    this.textColor = textColor;

  }

  /**
   * Create Button for Hud
   *
   * @param label           Button Label
   * @param width           Button Width
   * @param height          Button height
   * @param x               Button start x
   * @param y               Button start y
   * @param backgroundColor Button Background Color (x=red, y=green, z=blue, w=alpha)
   * @param textColor       Button Text Color (x=red, y=green, z=blue)
   */
  public Button(String label, String code, float width, float height, float x, float y, Vector4f backgroundColor, Vector3f textColor) {
    this(label, code, new Vector2f(width, height), new Vector2f(x, y), backgroundColor, textColor);
  }

  public void setBackgroundColor(Vector4f backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setTextColor(Vector3f textColor) {
    this.textColor = textColor;
  }


  /**
   * Render Button
   */
  public void renderButton() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawRect(startPosition, size, backgroundColor);
    nanoVGUI.drawText(
            this.label,
            startPosition.x + 5,
            startPosition.y + 5,
            18.0f, // font size
            1.0f,
            1.0f,
            1.0f // White color (RGB)
    );
  }

  public boolean isHovered(double mouseX, double mouseY) {
    return mouseX >= startPosition.x && mouseX <= endPosition.x &&
            mouseY >= startPosition.y && mouseY <= endPosition.y;
  }

  public String getCode() {
    return code;
  }
}
