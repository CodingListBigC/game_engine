package org.bigacl.renderEngine.gui.drawing;

import org.bigacl.renderEngine.gui.drawing.types.CircleShape;
import org.bigacl.renderEngine.gui.drawing.types.IconWithText;
import org.bigacl.renderEngine.gui.drawing.types.RectangleShape;
import org.bigacl.renderEngine.gui.drawing.types.RegularText;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.system.MemoryUtil.*;

public class NanoVGUI implements RectangleShape, CircleShape, RegularText, IconWithText {
  private long vg;

  @Override
  public long getVg(){
    return vg;
  }

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
      int fontRegular = NanoVG.nvgCreateFont(vg, "sans", regularFont.getAbsolutePath());
      int fontNerd = NanoVG.nvgCreateFont(vg, "nerd", nerdFont.getAbsolutePath());

      if (fontRegular == -1) {
        throw new RuntimeException("Failed to create regular font");
      }
      if (fontNerd == -1) {
        throw new RuntimeException("Failed to create Nerd font");
      }

      System.out.println("Fonts loaded successfully!");

    } catch (Exception e) {
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
    return nvgTextBounds(vg, 0, 0, text, bounds);
  }

  // Manual word-wrap: splits text into lines that fit within maxWidth
  public static NVGColor convertColor(Color awtColor, NVGColor store) {
    if (store == null) {
      store = NVGColor.create(); // Create a new buffer-backed object if none provided
    }
    float r = awtColor.getRed() / 255.0f;
    float g = awtColor.getGreen() / 255.0f;
    float b = awtColor.getBlue() / 255.0f;
    float a = awtColor.getAlpha() / 255.0f;

    // The nvgRGBAf function populates the 'store' object directly
    nvgRGBAf(r, g, b, a, store);

    return store;
  }

}
