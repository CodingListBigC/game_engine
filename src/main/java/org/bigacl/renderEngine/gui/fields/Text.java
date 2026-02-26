package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.xml.stream.Location;

public class Text {
  private String text;
  private int type;
  private Vector2f size = new Vector2f(0,0);

  private Vector2f position = new Vector2f(0.0f);
  private final Vector3f textColor;

  public Text(String text, int type) {
    this.text = text;
    this.type = type;
    this.textColor = new Vector3f(0.0f);
  }

  public Text() {
    this.textColor = new Vector3f(Const.DEFAULT_TEXT_COLOR);
  }

  public Text(String text, int type, Vector2f position, Vector3f textColor, Vector2f size) {
    this.text = text;
    this.type = type;
    this.position = position;
    this.textColor = textColor;
    this.size = size;
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
  public void render(Vector2f position, Vector3f color){
    ClassConst.nanoVGUI.drawText(text,ClassConst.fontSizing.getFontSize(type),position,color);
  }


  public float getHeight() {
    return size.y;
  }

  public void setText(String displayString) {
    this.text = displayString;
  }

  public void renderLimits(Vector2f position, Vector2f size, Vector3f textColor) {
    this.position = position;
    ClassConst.nanoVGUI.drawTextFitToBoxCentered(text,position,size,ClassConst.fontSizing.getFontSize(type),textColor);
  }
  public void renderLimits() {
    ClassConst.nanoVGUI.drawTextFitToBoxCentered(text,this.position,size,ClassConst.fontSizing.getFontSize(type),textColor);
  }

  public void setPosition(Vector2f position) {
    this.position = position;
  }
}
