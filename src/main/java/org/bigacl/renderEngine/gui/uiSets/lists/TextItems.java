package org.bigacl.renderEngine.gui.uiSets.lists;

import org.bigacl.renderEngine.gui.fields.Text;
import org.joml.Vector2f;

import java.util.Objects;

public class TextItems {
  private int type;
  private final String title;
  private String info;
  private String displayString = null;
  private final Text display;

  public TextItems(int type, String title, String info) {
    this.type = type;
    this.title = title;
    this.info = info;
    updateDisplayText();
    this.display = new Text(displayString, this.type);
  }

  public void updateDisplayText() {
    if (Objects.equals(this.info, "")) {
      displayString = this.title;
      return;
    }
    displayString = title + ": " + info;
    if (this.display != null) {
      this.display.setText(displayString);
      this.display.updateSize();
    }
  }

  public void updateInfo(String info) {
    this.info = info;
    updateDisplayText();
  }

  public Text getDisplay() {
    return display;
  }

  public Vector2f getSize() {
    return display.getSize(true);
  }

  public void updateSize() {
    display.updateSize();
  }

  public int getType() {
    return type;
  }
}