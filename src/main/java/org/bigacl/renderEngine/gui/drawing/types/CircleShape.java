package org.bigacl.renderEngine.gui.drawing.types;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

import static org.bigacl.renderEngine.gui.drawing.NanoVGUI.convertColor;
import static org.lwjgl.nanovg.NanoVG.*;

public interface CircleShape {
  long getVg();
  default void drawCircle(Vector2f position, float diameter, Color backgroundColor, Color outlineColor){
    long vg = getVg();
    nvgBeginPath(vg);
    // nvgCircle(context, centerX, centerY, radius);
    nvgCircle(vg, position.x, position.y, diameter / 2);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor bgColor = convertColor(backgroundColor,null);
      nvgFillColor(vg, bgColor); // Set color to red
      nvgFill(vg); // Fill the circle
      NVGColor olColor = convertColor(outlineColor,null);
      nvgFillColor(vg, olColor); // Set color to red
      nvgFill(vg); // Fill the circle
    }
  }
}
