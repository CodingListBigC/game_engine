package org.bigacl.renderEngine.gui.fields;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

public interface InputInterface {
  void render();
  void checkInput(Vector2d mouseLocation, int mouseAction);
}
