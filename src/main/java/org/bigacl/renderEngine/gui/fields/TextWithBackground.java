package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.*;

public class TextWithBackground extends Text{
  private Color backgroundColor = new Color(0.1f,0.1f,0.1f,1);
  private Color outlineColor = new Color(0.1f,0.1f,0.1f,1);

  public TextWithBackground(String text) {
    super(text);
  }

  public void renderBg() {
    ClassConst.nanoVGUI.drawRect(getPosition(),sizeLimits,backgroundColor);
    super.renderLimits();
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }
}
