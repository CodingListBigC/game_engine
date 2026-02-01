package org.bigacl.renderEngine.gui.font;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;

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

  public void drawText(String text, float x, float y, float size, float r, float g, float b) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, 1.0f, color);

      nvgFontFace(vg, "sans");
      nvgFontSize(vg, size);
      nvgFillColor(vg, color);
      nvgText(vg, x, y, text);
    }
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
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, a, color);

      nvgBeginPath(vg);
      nvgRect(vg, x, y, w, h);
      nvgFillColor(vg, color);
      nvgFill(vg);
    }
  }

  public void drawRoundedRect(float x, float y, float w, float h, float radius,
                              float r, float g, float b, float a) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      NVGColor color = NVGColor.malloc(stack);
      nvgRGBAf(r, g, b, a, color);

      nvgBeginPath(vg);
      nvgRoundedRect(vg, x, y, w, h, radius);
      nvgFillColor(vg, color);
      nvgFill(vg);
    }
  }

  public void cleanup() {
    nvgDelete(vg);
  }
}