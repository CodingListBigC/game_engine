package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opencl.CLBusAddressAMD;

import java.awt.*;

public class Text {
  private String text;
  private int type;
  private Vector2f size = new Vector2f(0,0);

  public void setSizeLimits(Vector2f sizeLimits) {
    this.sizeLimits = sizeLimits;
  }

  protected Vector2f sizeLimits = new Vector2f();

  private Vector2f position = new Vector2f(0.0f);
  private Color textColor;

  public Text(String text, int type) {
    this.text = text;
    this.type = type;
    this.textColor = Color.BLACK;
  }

  public Text() {
    this.textColor = Color.WHITE;
  }

  public Text(String text, int type, Vector2f position, Color textColor, Vector2f size) {
    this.text = text;
    this.type = type;
    this.position = position;
    this.textColor = textColor;
    this.size = size;
  }

  public Text(String text) {
    this.text = text;
    this.textColor = Color.BLACK;
  }

  public Vector2f getSize(){
    if (size.x == 0 && text != null && !text.isEmpty()) {
      updateSize();
    }else if(this.size.y <= 0){
      updateSize();
    }
    return size;
  }
  public Vector2f getSize(boolean update){
    if (update){
      this.updateSize();
    }
    return this.getSize();
  }

  public void updateSize() {
    float sizeVal = ClassConst.fontSizing.getFontSize(type);
    this.size.x = ClassConst.nanoVGUI.getTextWidth(text, sizeVal);
    this.size.y = ClassConst.fontSizing.getStandardHeight(ClassConst.nanoVGUI.getVg(), type);
    if (this.size.y <= 0) {
      this.size.y = this.size.y * -1;
    }
  }
  public void render(Vector2f position, Color color){
    ClassConst.nanoVGUI.drawText(text,ClassConst.fontSizing.getFontSize(type),position,color);
  }
  public void render(){
    ClassConst.nanoVGUI.drawText(text,ClassConst.fontSizing.getFontSize(type),position,textColor);
  }


  public float getHeight() {
    return size.y;
  }

  public void setText(String displayString) {
    this.text = displayString;
  }

  public void renderLimits(Vector2f position, Vector2f size, Color textColor) {
    this.position = position;
    ClassConst.nanoVGUI.drawTextFitToBoxCentered(text,position,size,ClassConst.fontSizing.getFontSize(type),textColor);
  }
  public void renderLimits() {
    ClassConst.nanoVGUI.drawTextFitToBoxCentered(text,this.position,sizeLimits,ClassConst.fontSizing.getFontSize(type),textColor);
  }

  public void setPosition(Vector2f position) {
    this.position = position;
  }

  public Vector2f getPosition() {
    return position;
  }

  public void setTextColor(Color textColor) {
    this.textColor = textColor;
  }
}
