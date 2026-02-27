package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.sets.InputSets;
import org.joml.Vector2f;

import java.awt.*;

public class SelectButtonSet extends InputSets {

  float buttonOffSetLeft = 10;
  final Color buttonBg = Color.lightGray;
  final Color buttonOl = Color.gray;
  @Override
  public void newDefaultItem() {
    Vector2f location = new Vector2f(buttonOffSetLeft, getNextY());
    addItem(new SelectButton(location,rowHeight,buttonBg,buttonOl));
  }
}
