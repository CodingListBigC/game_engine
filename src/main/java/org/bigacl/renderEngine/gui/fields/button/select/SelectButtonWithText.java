package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.TextWithBackground;
import org.bigacl.renderEngine.gui.fields.sets.InputWithText;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.awt.*;

public class SelectButtonWithText extends InputWithText{


  SelectButton selectButton;
  protected Vector2f location;
  protected Vector2f rowSize;
  protected Color buttonBg;
  protected Color buttonOl;
  protected Color fontColor;

  public SelectButtonWithText(Vector2f location, Vector2f rowSize, Color buttonBg, Color buttonOl, Color fontColor) {
    super();
    this.location = location;
    this.rowSize = rowSize;
    this.buttonBg = buttonBg;
    this.buttonOl = buttonOl;
    this.fontColor = fontColor;

    createItems();
  }

  protected void createItems(){
    this.text = new TextWithBackground("");
    this.text.setTextColor(fontColor);
    this.text.setSizeLimits(new Vector2f((rowSize.x - (columnSpacing * 4) - rowSize.y), rowSize.y));
    this.text.setPosition(new Vector2f((columnSpacing *2) + rowSize.y, 0f).add(this.location));
    this.selectButton = new SelectButton(new Vector2f(this.location).add(columnSpacing,0f),this.rowSize.y,buttonBg,buttonOl);
  }

  @Override
  protected void setText() {
    this.text = new TextWithBackground("");
  }

  @Override
  public void renderInput() {
    selectButton.render();
  }
  @Override
  public void rightClick() {

  }

  @Override
  public void leftClick() {

  }

  @Override
  public boolean isHovered(Vector2d mouseLocation) {
    return selectButton.isHovered(mouseLocation);
  }

  @Override
  public void setClickedStatus(boolean clicked) {
    selectButton.setClickedStatus(clicked);
  }

  @Override
  public boolean getClickedStatus() {
    return selectButton.getClickedStatus();
  }
}
