package org.bigacl.renderEngine.gui.font;

import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.*;

public class NanoVGUI {
  private long vg;
  private int fontRegular;
  private int fontNerd;

  public void init() {
    vg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
    if (vg == NULL) {
      throw new RuntimeException("Could not init NanoVG");
    }

    try {
      // Extract fonts from resources to temp files
      File regularFont = extractFontToTemp("fonts/Roboto-Regular.ttf");
      File nerdFont = extractFontToTemp("fonts/JetBrainsMonoNerdFont-Regular.ttf");

      // Load fonts using file paths
      fontRegular = nvgCreateFont(vg, "sans", regularFont.getAbsolutePath());
      fontNerd = nvgCreateFont(vg, "nerd", nerdFont.getAbsolutePath());

      if (fontRegular == -1) {
        throw new RuntimeException("Failed to create regular font");
      }
      if (fontNerd == -1) {
        throw new RuntimeException("Failed to create Nerd font");
      }

      System.out.println("Fonts loaded successfully!");

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Could not load fonts", e);
    }
  }

  private File extractFontToTemp(String resourcePath) throws Exception {
    InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
    if (in == null) {
      throw new RuntimeException("Font not found: " + resourcePath);
    }

    // Create temp file
    String fileName = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
    File tempFile = File.createTempFile("font_", "_" + fileName);
    tempFile.deleteOnExit();

    // Copy resource to temp file
    try (FileOutputStream out = new FileOutputStream(tempFile)) {
      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
    }

    System.out.println("Extracted font to: " + tempFile.getAbsolutePath());
    return tempFile;
  }

  public void beginFrame(int windowWidth, int windowHeight, float pixelRatio) {
    nvgBeginFrame(vg, windowWidth, windowHeight, pixelRatio);
  }

  public void endFrame() {
    nvgEndFrame(vg);
  }

  /**
   * Draw Text Function spreadOut
   *
   * @param text       = Text show on screen.
   * @param x          = X position of text.
   * @param y          = Y position of text.
   * @param size       = Size of text.
   * @param redColor   = Red color of text.
   * @param greenColor = Green colro of text,
   * @param blueColor  = Blue color of text.
   */
  public void drawText(String text, float x, float y, float size, float redColor, float greenColor, float blueColor) {
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
   * @param text     = Text show on screen.
   * @param size     = Size of text.
   * @param position = Position of text.
   * @param color    = Color of text.
   */
  public void drawText(String text, float size, Vector2f position, Vector3f color) {
    drawText(text, position.x, position.y, size, color.x, color.y, color.z);
  }

  /**
   * Draw Text function with position short.
   *
   * @param text     = Text show on screen.
   * @param startPos = Start position on screen.
   * @param position = Item position in screen.
   * @param size     = Size of text.
   * @param color    = Color of text.
   */
  public void drawText(String text, Vector2f startPos, int position, float size, Vector3f color) {
    drawText(text, startPos.x, (startPos.y + (position * (size + Const.PADDING))), size, color.x, color.y, color.z);
  }

  /**
   * Draw Text Function with position long.
   *
   * @param text       = Text show on screen.
   * @param x          = Starting x position.
   * @param y          = Starting y position.
   * @param position   = Item position in screen.
   * @param size       = Size of text.
   * @param redColor   = Red Color of text.
   * @param greenColor = Green color of text.
   * @param blueColor  = Blue color of Text
   */
  public void drawText(String text, float x, float y, int position, float size, float redColor, float greenColor, float blueColor) {
    Vector2f startPos = new Vector2f(x, y);
    Vector3f color = new Vector3f(redColor, greenColor, blueColor);
    drawText(text, startPos.x, (startPos.y + (position * (size + Const.PADDING))), size, color.x, color.y, color.z);
  }

  public void drawTextFitToBox(String text, float x, float y, float sizeX, float sizeY, float startFontSize, float r, float g, float b) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      float currentSize = startFontSize;
      float[] bounds = new float[4];
      FloatBuffer boundsBuf = stack.mallocFloat(4);

      // 1. Configure initial font state
      nvgFontFace(vg, "sans");
      nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);

      // 2. The Scaling Loop
      // We decrease the font size until the text fits both width and height
      while (currentSize > 1.0f) {
        nvgFontSize(vg, currentSize);

        // Measure the text box at this specific font size
        // nvgTextBoxBounds tells us how big the wrapped text will be
        nvgTextBoxBounds(vg, x, y, sizeX, text, boundsBuf);

        float textW = boundsBuf.get(2) - boundsBuf.get(0);
        float textH = boundsBuf.get(3) - boundsBuf.get(1);

        // If it fits inside our Vector2f, we stop shrinking
        if (textW <= sizeX && textH <= sizeY) {
          break;
        }

        // Shrink and try again
        currentSize -= 0.5f;
      }

      // 3. Render the text at the calculated "best fit" size
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, 1.0f, color);
      nvgFillColor(vg, color);

      // We still use Scissor as a safety net for "unbreakable" long words
      nvgScissor(vg, x, y, sizeX, sizeY);
      nvgTextBox(vg, x, y, sizeX, text);
      nvgResetScissor(vg);
    }
  }

  public void drawTextFitToBox(String text, Vector2f startPos, Vector2f boxSize, float startFontSize, Vector3f textColor){
    this.drawTextFitToBox(text, startPos.x, startPos.y, boxSize.x, boxSize.y, startFontSize, textColor.x, textColor.y, textColor.z);
  }


  public void drawNerdIcon(String icon, float x, float y, float size, float r, float g, float b) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, 1.0f, color);

      nvgFontFace(vg, "nerd");
      nvgFontSize(vg, size);
      nvgFillColor(vg, color);
      nvgText(vg, x, y, icon);
    }
  }

  public void drawRect(float x, float y, float w, float h, float r, float g, float b, float a) {
    nvgBeginPath(vg);
    nvgRect(vg, x, y, w, h);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.calloc(stack);
      nvgRGBAf(r, g, b, a, color);
      nvgFillColor(vg, color);
    }

    nvgFill(vg);
  }


  public void drawRect(Vector2f position, Vector2f rectSize, Vector4f backgroundColor) {
    drawRect(position.x, position.y, rectSize.x, rectSize.y, backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
  }

  public void drawRoundedRect(float radius, float x, float y, float w, float h, float r, float g, float b, float a) {
    nvgBeginPath(vg);
    nvgRoundedRect(vg, x, y, w, h, radius); // radius in pixels

    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor nvgColor = NVGColor.calloc(stack);
      nvgRGBAf(r, g, b, a, nvgColor);
      nvgFillColor(vg, nvgColor);
    }
    nvgFill(vg);
  }

  public void drawRoundedRect(float radius, Vector2f position, Vector2f rectSize, Vector4f backgroundColor) {
    drawRoundedRect(radius, position.x, position.y, rectSize.x, rectSize.y, backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
  }

  /**
   * Draw Nerd icon with text next to it
   *
   * @param icon     = Nerd font icon (e.g., "", "")
   * @param text     = Text to display next to icon
   * @param x        = X position
   * @param y        = Y position
   * @param iconSize = Size of the icon
   * @param textSize = Size of the text
   * @param r        = Red color
   * @param g        = Green color
   * @param b        = Blue color
   */
  public void drawIconWithText(String icon, String text, float x, float y, float iconSize, float textSize, float r, float g, float b) {
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
   * @param text     = Text to display
   * @param position = Position vector
   * @param iconSize = Size of icon
   * @param textSize = Size of text
   * @param color    = RGB color vector
   */
  public void drawIconWithText(String icon, String text, Vector2f position, float iconSize, float textSize, Vector3f color) {
    drawIconWithText(icon, text, position.x, position.y, iconSize, textSize, color.x, color.y, color.z);
  }

  /**
   * Draw Nerd icon with text - With position index
   *
   * @param icon     = Nerd font icon
   * @param text     = Text to display
   * @param startPos = Starting position
   * @param position = Item position index
   * @param iconSize = Size of icon
   * @param textSize = Size of text
   * @param color    = RGB color vector
   */
  public void drawIconWithText(String icon, String text, Vector2f startPos, int position, float iconSize, float textSize, Vector3f color) {
    float y = startPos.y + (position * (Math.max(iconSize, textSize) + Const.PADDING));
    drawIconWithText(icon, text, startPos.x, y, iconSize, textSize, color.x, color.y, color.z);
  }

  public void cleanup() {
    nvgDelete(vg);
  }

  public float[] getTextSize(String text, float size) {
    // 1. Set the state so NanoVG knows which font/size we are measuring
    nvgFontFace(vg, "sans");
    nvgFontSize(vg, size);

    // 2. Prepare a float array to hold the bounds [x1, y1, x2, y2]
    float[] bounds = new float[4];

    // 3. Measure the text
    float width = nvgTextBounds(vg, 0, 0, text, bounds);

    // bounds[3] - bounds[1] gives the actual height
    float height = bounds[3] - bounds[1];

    return new float[]{width, height};
  }

  public float getTextWidth(String text, float size) {
    // 1. Set the state so NanoVG knows which font/size we are measuring
    nvgFontFace(vg, "sans");
    nvgFontSize(vg, size);

    // 2. Prepare a float array to hold the bounds [x1, y1, x2, y2]
    float[] bounds = new float[4];

    // 3. Measure the text
    float width = nvgTextBounds(vg, 0, 0, text, bounds);

    return width;
  }


  public long getVg() {
    return vg;
  }

}
