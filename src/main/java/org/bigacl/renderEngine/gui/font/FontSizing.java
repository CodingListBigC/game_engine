package org.bigacl.renderEngine.gui.font;

import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.nanovg.NanoVG.*;

public class FontSizing {
  public final int HEADING_1_SIZE;
  public final int HEADING_2_SIZE;
  public final int HEADING_3_SIZE;
  public final int HEADING_4_SIZE;
  public final int NORMAL_TEXT_SIZE;

  // Cache to store the fixed height for each type to prevent vertical jitter
  private final Map<Integer, Float> heightCache = new HashMap<>();

  public FontSizing(int normalTextSize) {
    this.NORMAL_TEXT_SIZE = normalTextSize;
    // Using a 1.25x (Major Third) or 1.125x (Major Second) scale
    this.HEADING_4_SIZE = (int) (this.NORMAL_TEXT_SIZE * 1.25);
    this.HEADING_3_SIZE = (int) (this.HEADING_4_SIZE * 1.25);
    this.HEADING_2_SIZE = (int) (this.HEADING_3_SIZE * 1.25);
    this.HEADING_1_SIZE = (int) (this.HEADING_2_SIZE * 1.25);
  }

  /**
   * Returns the pixel font size for a given type.
   */
  public int getFontSize(int type) {
    switch (type) {
      case 1:
        return HEADING_1_SIZE;
      case 2:
        return HEADING_2_SIZE;
      case 3:
        return HEADING_3_SIZE;
      case 4:
        return HEADING_4_SIZE;
      case 0:
      default:
        return NORMAL_TEXT_SIZE;
    }
  }

  /**
   * Gets a consistent height for the font type.
   * This ensures your HUD doesn't jump up and down when text changes.
   */
  public float getStandardHeight(long vg, int type) {
    if (!heightCache.containsKey(type)) {
      nvgFontSize(vg, getFontSize(type));
      nvgFontFace(vg, "sans");

      float[] ascender = new float[1];
      float[] descender = new float[1];
      float[] lineh = new float[1];

      nvgTextMetrics(vg, ascender, descender, lineh);

      float totalHeight = descender[0] - ascender[0];

      // Safety: If the font isn't loaded, don't cache 0.0
      if (totalHeight > 0) {
        heightCache.put(type, totalHeight);
      } else {
        return 20.0f; // Fallback height so the list doesn't collapse
      }
    }
    return heightCache.get(type);
  }
}