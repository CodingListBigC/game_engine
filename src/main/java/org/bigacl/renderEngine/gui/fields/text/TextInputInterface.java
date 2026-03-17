package org.bigacl.renderEngine.gui.fields.text;

public interface TextInputInterface {
  void onCharacterTyped(int codePoint);

  void onKeyPressed(int key, int action);
}
