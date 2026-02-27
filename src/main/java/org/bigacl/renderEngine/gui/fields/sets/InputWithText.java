package org.bigacl.renderEngine.gui.fields.sets;

import org.bigacl.renderEngine.gui.fields.Text;
import org.bigacl.renderEngine.gui.fields.TextWithBackground;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;

public abstract class InputWithText {
  // Text Info
  protected TextWithBackground text;
  private final int fontType = ClassConst.fontSizing.NORMAL_TEXT_SIZE;
  // Text Size
  protected Vector2f textSize;
  protected Vector2f textPosition;

  protected boolean viewStatus;
  public void render(){
    if (!this.viewStatus)
      return;
    renderInput();
    renderText();
  };
  public void renderText(){
    text.render();
  };
  public abstract void renderInput();
  public void setTextLabel(String textLabel){
    this.text.setText(textLabel);
  }
  public void setViewStatus(boolean viewStatus) {
    this.viewStatus = viewStatus;
  }

}
