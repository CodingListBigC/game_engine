package org.bigacl.renderEngine.gui.fields;

import org.bigacl.renderEngine.gui.fields.text.TextInputInterface;
import org.bigacl.renderEngine.gui.font.FontSizing;
import org.bigacl.renderEngine.utils.consts.ClassConst;

import java.awt.*;

public abstract class TextBoxAbstract extends InputInterface implements TextInputInterface {
  // Render Settings
  boolean setBoxLimits = false;
  boolean wrapText = false;

  // Text Settings
  StringBuilder text = new StringBuilder();
  TextWithBackground renderText = new TextWithBackground();
  FontSizing fontSizing = ClassConst.fontSizing;

  boolean isFocused = false;

  public TextBoxAbstract(Color backgroundColor, Color outlineColor, Color textColor) {
    renderText.setTextColor(backgroundColor);
    renderText.setBackgroundColor(backgroundColor);
    renderText.setOutlineColor(outlineColor);
  }

  public TextBoxAbstract() {
  }

  @Override
  public void render() {
    renderText.setText(text.toString());
    renderText.render();
  }

  @Override
  public void leftClick() {
    this.isFocused = true;
  }

  public void onCharacterTyped(int codePoint) {
    if (!isFocused) return;

    // Convert codePoint to a char and append
    char c = (char) codePoint;

    // Optional: Limit text length so it doesn't leave the box
    if (text.length() < 20) {
      text.append(c);
    }
  }

  public void onKeyPressed(int key, int action) {
    if (!isFocused || action == 0) return; // 0 is GLFW_RELEASE

    if (key == 259) { // GLFW_KEY_BACKSPACE
      if (text.length() > 0) {
        text.deleteCharAt(text.length() - 1);
      }
    }
    if (key == 257) { // GLFW_KEY_ENTER
      isFocused = false; // "Submit" and unfocus
    }
  }
}

