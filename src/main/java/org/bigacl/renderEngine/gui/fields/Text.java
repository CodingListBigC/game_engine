package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Text {
  private String text;
  /** Code for text */
  private final String code;
  private final Vector2f startPos;
  private final Vector2f boxSize;
  private final Vector3f textColor;
  private float defaultFontSize = 20;

  public Text(String text, String code, Vector2f startPos, Vector2f boxSize, Vector3f textColor) {
    this.text = text;
    this.code = code;
    this.startPos = startPos;
    this.boxSize = boxSize;
    this.textColor = textColor;
  }

  public Text(String text, String code, Vector2f startPos, Vector2f boxSize, Vector3f textColor, float defaultFontSize) {
    this(text,code,startPos,boxSize,textColor);
    this.defaultFontSize = defaultFontSize;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void render(){
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawTextFitToBox(this.text, this.startPos, this.boxSize,this.defaultFontSize,this.textColor);
  }

  public String getCode() {
    return code;
  }
}
