package org.bigacl.renderEngine.gui.fields.button.select;

import org.bigacl.renderEngine.gui.fields.sets.InputWithText;

public class SelectButtonWithText extends InputWithText {
  SelectButton selectButton;

  @Override
  public void renderInput() {
    selectButton.render();
  }
}
