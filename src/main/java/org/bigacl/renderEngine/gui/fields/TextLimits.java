package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;

import java.awt.*;

public class TextLimits {
  private String text;
  /**
   * Code for text
   */
  private final String code;
  private final Vector2f startPos;
  private final Vector2f boxSize;
  private final Color textColor;

  public TextLimits(String text, String code, Vector2f startPos, Vector2f boxSize, Color textColor) {
    this.text = text;
    this.code = code;
    this.startPos = startPos;
    this.boxSize = boxSize;
    this.textColor = textColor;
  }

  public void setText(String text) {
    System.out.println("Update Text: " + text);
    this.text = text;
  }

  public void render() {
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    float defaultFontSize = 20;
    nanoVGUI.drawTextFitToBoxCentered(this.text, this.startPos, this.boxSize, defaultFontSize, this.textColor);
  }

  public String getCode() {
    return code;
  }

  public void debugLog() {
    System.out.print("Text: " + text + ",");
    System.out.print("BoxSize: " + boxSize + ",");
    System.out.println();
  }

}
