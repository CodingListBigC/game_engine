package org.bigacl.renderEngine.gui.fields.sets;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.TextWithBackground;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;

public abstract class InputWithText extends InputInterface {
  protected float columnSpacing= 10;
  // Text Info
  protected TextWithBackground text;
  private final int fontType = ClassConst.fontSizing.NORMAL_TEXT_SIZE;
  // Text Size
  protected Vector2f textSize;
  protected Vector2f textPosition;

  public void render(){
    if (!this.visible)
      return;
    renderInput();
    renderText();
  };
  public void renderText(){
    if (text == null){
      return;
    }
    text.render();
  };
  public abstract void renderInput();
  public void setTextLabel(String textLabel){
    this.text.setText(textLabel);
  }

}
