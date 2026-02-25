package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SelectButton extends InputAbstract{
  private final float diameter;
  private final Vector4f mainBackgroundColor;
  private final Vector4f outlineColor;
  private boolean clickedStatus;
  private final Vector2f position;

  public SelectButton(Vector2f position, float diameter, Vector4f mainBackgroundColor, Vector4f outlineColor) {
    this.position = position;
    this.diameter = diameter;
    this.mainBackgroundColor = mainBackgroundColor;
    this.outlineColor = outlineColor;
  }

  @Override
  public void render(){
    if (clickedStatus)
      this.renderNotClicked();
    else
      this.renderClicked();
  }
  private void renderNotClicked(){
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawCircle(this.position,diameter,this.mainBackgroundColor,this.mainBackgroundColor);
  }
  private void renderClicked(){
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawCircle(this.position,diameter,this.mainBackgroundColor,this.outlineColor);
  }

  public void toggleClickedStatus() {
    clickedStatus = !clickedStatus;
  }
}
