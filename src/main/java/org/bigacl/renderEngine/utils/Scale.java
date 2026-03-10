package org.bigacl.renderEngine.utils;

import org.joml.Vector3f;

public class Scale {
  // Constant for precision
  public static final double SCALE_TO_FT = 3.280839895;
  public static final float FT_TO_MT = 0.3048f;

  /**
   * Converts feet to meters
   * Use this when reading 'pos' or 'size' from your house.json
   */
  public static Vector3f feetToMeters(Vector3f feetVector) {
    return new Vector3f(feetVector).mul(FT_TO_MT);
  }

  /**
   * If you are passing individual floats from your getInfo buttons
   */
  public static float ftToM(float feet) {
    return feet * FT_TO_MT;
  }

  public static float getScale(String type) {
    if ("feet".equalsIgnoreCase(type))
      return (float) SCALE_TO_FT;
    else if ("meter".equalsIgnoreCase(type))
      return 1;
    else
      System.err.println("Type does not have scale: " + type);
    return 1;
  }
}
