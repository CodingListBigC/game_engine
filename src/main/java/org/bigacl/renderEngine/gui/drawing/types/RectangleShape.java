package org.bigacl.renderEngine.gui.drawing.types;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

import static org.bigacl.renderEngine.gui.drawing.NanoVGUI.convertColor;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgRoundedRect;

public interface RectangleShape{
  long getVg();
  default void drawRect(float x, float y, float w, float h, float r, float g, float b, float a) {
    drawRoundedRect(0, new Vector2f(x,y),new Vector2f(w,h),new Color(r,g,b,a));
  }

  default void drawRect(Vector2f position, Vector2f rectSize, Color backgroundColor) {
    drawRoundedRect(0,position,rectSize,backgroundColor);
  }

  default void drawRoundedRect(float radius,  Vector2f position, Vector2f rectSize, Color backgroundColor) {
    long vg = getVg();
    nvgBeginPath(vg);
    nvgRoundedRect(vg, position.x, position.y, rectSize.x, rectSize.y, radius); // radius in pixels
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor nvgColor = convertColor(backgroundColor,null);
      nvgFillColor(vg, nvgColor);
    }
    nvgFill(vg);
  }
}
