package org.bigacl.renderEngine.gui.fields;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class SelectButton {
  private Vector2f size;
  private Vector4f mainBackgroundColor;
  private Vector4f selectedBackgroundColor;
  private boolean clickedStatus;

  public SelectButton(Vector2f size, Vector4f mainBackgroundColor, Vector4f selectedBackgroundColor) {
    this.size = size;
    this.mainBackgroundColor = mainBackgroundColor;
    this.selectedBackgroundColor = selectedBackgroundColor;
  }

  public void render(){
    if (clickedStatus)
      this.renderNotClicked();
    else
      this.renderClicked();
  }
  private void renderNotClicked(){
  }
  private void renderClicked(){}

  public void toggleClickedStatus() {
    clickedStatus = !clickedStatus;
  }
}
