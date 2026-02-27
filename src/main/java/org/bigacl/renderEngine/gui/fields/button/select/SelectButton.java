package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.button.ClickAbstract;
import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;

import java.awt.*;

public class SelectButton extends ClickAbstract{
  private boolean clickedStatus;




  public SelectButton(Vector2f location, float diameter, Color mainBackgroundColor, Color outlineColor) {
    this.location = location;
    this.size = new Vector2f(diameter);
    this.backgroundColor = mainBackgroundColor;
    this.outlineColor = outlineColor;
  }

  @Override
  public void render(){
    if (clickedStatus)
      this.renderClicked();
    else
      this.renderNotClicked();
  }


  private void renderNotClicked(){
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawCircle(this.location,size.x,this.backgroundColor,this.backgroundColor);
  }
  private void renderClicked(){
    NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
    nanoVGUI.drawCircle(this.location,size.x,this.backgroundColor,this.outlineColor);
  }

  public void toggleClickedStatus() {
    clickedStatus = !clickedStatus;
  }
  @Override
  public void rightClick() {

  }

  @Override
  public void leftClick() {

  }
  @Override
  public void setClickedStatus(boolean clicked){
    clickedStatus = clicked;
  }

  @Override
  public boolean getClickedStatus(){
    return clickedStatus;
  }
}
