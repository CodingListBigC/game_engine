package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.sets.InputSets;
import org.joml.Vector2f;

import java.awt.*;

public class SelectButtonSet extends InputSets {

  float columnWidth = 10;
  final Color buttonBg = Color.lightGray;
  final Color buttonOl = Color.gray;
  final Color fontColor = Color.white;
  final Vector2f setPos;
  @Override
  public void newDefaultItem() {
    Vector2f location = new Vector2f(columnWidth, getNextY()).add(this.setPos);
    addItem(new SelectButtonWithText(location,this.rowSize,this.buttonBg,this.buttonOl, this.fontColor));
  }

  public SelectButtonSet(Vector2f setPos) {
    this.setPos = new Vector2f(setPos);
    checkAmount(10);
    viewAmount(4);
  }
}
