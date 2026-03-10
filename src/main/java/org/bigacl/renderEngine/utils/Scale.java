package org.bigacl.renderEngine.utils;

import org.joml.Vector3f;

public class Scale {
  // Constant for precision
  public static final float FT_TO_M = 0.3048f;

  /**
   * Converts feet to meters
   * Use this when reading 'pos' or 'size' from your house.json
   */
  public static Vector3f feetToMeters(Vector3f feetVector) {
    return new Vector3f(feetVector).mul(FT_TO_M);
  }

  /**
   * If you are passing individual floats from your getInfo buttons
   */
  public static float ftToM(float feet) {
    return feet * FT_TO_M;
  }

  public static float getScale(String type) {
    if (type == "feet")
      return FT_TO_M;
    else if (type == "meter")
      return 1;
    else
      System.err.println("Type does not have scale: " + type);
    return 1;
  }
}
