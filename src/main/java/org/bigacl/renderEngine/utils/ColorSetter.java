package org.bigacl.renderEngine.utils;

import java.awt.*;

public class ColorSetter {
  public static Color setNewAlpha(Color original, int alpha) {
    // Ensure alpha is within the valid range [0, 255]
    if (alpha < 0) alpha = 0;
    if (alpha > 255) alpha = 255;

    return new Color(original.getRed(), original.getGreen(), original.getBlue(), alpha);
  }
}
