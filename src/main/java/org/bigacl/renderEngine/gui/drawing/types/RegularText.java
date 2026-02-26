package org.bigacl.renderEngine.gui.drawing.types;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;
import static org.lwjgl.nanovg.NanoVG.nvgStroke;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeColor;
import static org.lwjgl.nanovg.NanoVG.nvgStrokeWidth;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;
import static org.lwjgl.nanovg.NanoVG.nvgTextBounds;
import static org.lwjgl.nanovg.NanoVG.nvgTextMetrics;

public interface RegularText {
  long getVg();
  /**
   * Draw TextLimits Function spreadOut
   *
   * @param text       = TextLimits show on screen.
   * @param x          = X position of text.
   * @param y          = Y position of text.
   * @param size       = Size of text.
   * @param redColor   = Red color of text.
   * @param greenColor = Green color of text,
   * @param blueColor  = Blue color of text.
   */
  default void drawText(String text, float x, float y, float size, float redColor, float greenColor, float blueColor) {
    long vg = getVg();
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
      nvgRGBAf(redColor, greenColor, blueColor, 1.0f, color);

      nvgFontFace(vg, "sans");
      nvgFontSize(vg, size);
      nvgFillColor(vg, color);
      nvgText(vg, x, y, text);
    }
  }

  /**
   * Draw Function short
   *
   * @param text     = TextLimits show on screen.
   * @param size     = Size of text.
   * @param position = Position of text.
   * @param color    = Color of text.
   */
  default void drawText(String text, float size, Vector2f position, Vector3f color) {
    drawText(text, position.x, position.y, size, color.x, color.y, color.z);
  }

  default void drawTextFitToBoxCentered(String text, float x, float y, float sizeX, float sizeY, float startFontSize, float r, float g, float b) {
    long vg = getVg();
    try (MemoryStack stack = MemoryStack.stackPush()) {
      nvgSave(vg);
      nvgFontFace(vg, "sans");
      nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);

      float currentSize = startFontSize;
      List<String> fittedLines = null;
      float lineHeight = 0;

      // --- 1. FIND FONT SIZE & WORD-WRAP ---
      while (currentSize > 1.0f) {
        nvgFontSize(vg, currentSize);

        FloatBuffer lineHeightBuf = stack.mallocFloat(1);
        FloatBuffer ascentBuf    = stack.mallocFloat(1);
        FloatBuffer descentBuf   = stack.mallocFloat(1);
        nvgTextMetrics(vg, ascentBuf, descentBuf, lineHeightBuf);
        lineHeight = lineHeightBuf.get(0);

        fittedLines = wordWrap(vg, text, sizeX, stack);
        float totalH = fittedLines.size() * lineHeight;

        if (totalH <= sizeY) {
          break;
        }
        currentSize -= 0.5f;
      }

      assert fittedLines != null;
      float totalTextH = fittedLines.size() * lineHeight;
      float startY = y + (sizeY - totalTextH) / 2.0f; // vertical center

      // --- 2. DEBUG BOX ---
      NVGColor debugColor = NVGColor.malloc(stack);
      nvgRGBAf(1.0f, 1.0f, 1.0f, 1.0f, debugColor);
      nvgBeginPath(vg);
      nvgRect(vg, x, y, sizeX, sizeY);
      nvgStrokeColor(vg, debugColor);
      nvgStrokeWidth(vg, 1.0f);
      nvgStroke(vg);

      // --- 3. DRAW EACH LINE MANUALLY CENTERED ---
      NVGColor textColor = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, 1.0f, textColor);
      nvgFillColor(vg, textColor);

      FloatBuffer boundsBuf = stack.mallocFloat(4);
      for (int i = 0; i < fittedLines.size(); i++) {
        String line = fittedLines.get(i);
        // Measure this line's actual pixel width
        nvgTextBounds(vg, 0, 0, line, boundsBuf);
        float lineW = boundsBuf.get(2) - boundsBuf.get(0);
        // Offset so line is centered in the box
        float lineX = x + (sizeX - lineW) / 2.0f;
        float lineY = startY + i * lineHeight;
        nvgText(vg, lineX, lineY, line);
      }

      nvgRestore(vg);
    }
  }

  default void drawTextFitToBoxCentered(String text, Vector2f startPos, Vector2f boxSize, float startFontSize, Color textColor){
    this.drawTextFitToBoxCentered(text, startPos.x, startPos.y, boxSize.x, boxSize.y, startFontSize, textColor.getRed(), textColor.getGreen(), textColor.getBlue());
  }

  private List<String> wordWrap(long vg, String text, float maxWidth, MemoryStack stack) {
    List<String> lines = new ArrayList<>();
    String[] words = text.split(" ");
    StringBuilder current = new StringBuilder();
    FloatBuffer bounds = stack.mallocFloat(4);

    for (String word : words) {
      String test = current.isEmpty() ? word : current + " " + word;
      nvgTextBounds(vg, 0, 0, test, bounds);
      float w = bounds.get(2) - bounds.get(0);
      if (w > maxWidth && !current.isEmpty()) {
        lines.add(current.toString());
        current = new StringBuilder(word);
      } else {
        current = new StringBuilder(test);
      }
    }
    if (!current.isEmpty()) lines.add(current.toString());
    return lines;
  }

}
