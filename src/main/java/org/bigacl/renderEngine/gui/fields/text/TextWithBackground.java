package org.bigacl.renderEngine.gui.fields.text;

import org.bigacl.renderEngine.utils.consts.ClassConst;

import java.awt.*;

public class TextWithBackground extends Text{
  private Color backgroundColor = new Color(0.1f,0.1f,0.1f,1);


  private Color outlineColor = new Color(0.1f,0.1f,0.1f,1);

  public TextWithBackground() {
  }

  public TextWithBackground(String text) {
    super(text);
  }

  public void renderBg() {
    drawBg();
    super.renderLimits();
  }
  public void drawBg(){

    ClassConst.nanoVGUI.drawRect(getPosition(),sizeLimits,backgroundColor,outlineColor);
  }
  @Override
  public void render(){
    drawBg();
    super.renderLimits();
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setOutlineColor(Color outlineColor) {
    this.outlineColor = outlineColor;
  }
}
