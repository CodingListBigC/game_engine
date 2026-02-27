package org.bigacl.renderEngine.gui.drawing.types;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

import static org.bigacl.renderEngine.gui.drawing.NanoVGUI.convertColor;
import static org.lwjgl.nanovg.NanoVG.*;

public interface CircleShape {
  float strokeSize = 1f;
  long getVg();
  default void drawCircle(Vector2f position, float diameter, Color backgroundColor){
    this.drawCircle(position,diameter,backgroundColor,backgroundColor, strokeSize);
  }

  default void drawCircle(Vector2f position, float diameter, Color backgroundColor, Color outlineColor){
    this.drawCircle(position,diameter,backgroundColor,outlineColor, strokeSize);
  }
  default void drawCircle(Vector2f position, float diameter, Color backgroundColor, Color outlineColor, float strokeSize){
    long vg = getVg();
    nvgBeginPath(vg);
    // nvgCircle(context, centerX, centerY, radius);
    nvgCircle(vg, position.x, position.y, diameter / 2);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor bgColor = convertColor(backgroundColor,null);
      nvgFillColor(vg, bgColor); // Set color to red
      nvgFill(vg); // Fill the circle
      NVGColor olColor = convertColor(outlineColor,null);
      nvgStrokeWidth(vg, strokeSize);
      nvgStrokeColor(vg, olColor); // Set color to red
      nvgStroke(vg); // Fill the circle
    }
  }
}
