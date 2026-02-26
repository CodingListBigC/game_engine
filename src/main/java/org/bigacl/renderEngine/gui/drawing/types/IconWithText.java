package org.bigacl.renderEngine.gui.drawing.types;

import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgText;

public interface IconWithText extends RegularText {
  long getVg();
  default void drawNerdIcon(String icon, float x, float y, float size, float r, float g, float b) {
    long vg = getVg();
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, 1.0f, color);

      nvgFontFace(vg, "nerd");
      nvgFontSize(vg, size);
      nvgFillColor(vg, color);
      nvgText(vg, x, y, icon);
    }
  }

  /**
   * Draw Nerd icon with text next to it
   *
   * @param icon     = Nerd font icon (e.g., "", "")
   * @param text     = TextLimits to display next to icon
   * @param x        = X position
   * @param y        = Y position
   * @param iconSize = Size of the icon
   * @param textSize = Size of the text
   * @param r        = Red color
   * @param g        = Green color
   * @param b        = Blue color
   */
  default void drawIconWithText(String icon, String text, float x, float y, float iconSize, float textSize, float r, float g, float b) {
    // Draw icon
    drawNerdIcon(icon, x, y, iconSize, r, g, b);

    // Draw text next to icon (offset by icon size + padding)
    float textX = x + iconSize + Const.PADDING;
    drawText(text, textX, y, textSize, r, g, b);
  }


  /**
   * Draw Nerd icon with text - Vector version
   *
   * @param icon     = Nerd font icon
   * @param text     = TextLimits to display
   * @param position = Position vector
   * @param iconSize = Size of icon
   * @param textSize = Size of text
   * @param color    = RGB color vector
   */
  default void drawIconWithText(String icon, String text, Vector2f position, float iconSize, float textSize, Color color) {
    float[] c = color.getRGBComponents(null);
    drawIconWithText(icon, text, position.x, position.y, iconSize, textSize, c[0], c[1], c[2]);
  }

  /**
   * Draw Nerd icon with text - With position index
   *
   * @param icon     = Nerd font icon
   * @param text     = TextLimits to display
   * @param startPos = Starting position
   * @param position = Item position index
   * @param iconSize = Size of icon
   * @param textSize = Size of text
   * @param color    = RGB color vector
   */
  default void drawIconWithText(String icon, String text, Vector2f startPos, int position, float iconSize, float textSize, Color color) {
    float y = startPos.y + (position * (Math.max(iconSize, textSize) + Const.PADDING));
    float[] c = color.getRGBComponents(null);
    drawIconWithText(icon, text, startPos.x, y, iconSize, textSize, c[0], c[1], c[2]);
  }
}
