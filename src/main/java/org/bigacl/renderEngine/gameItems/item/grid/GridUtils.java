package org.bigacl.renderEngine.gameItems.item.grid;

import org.joml.Vector3f;

public class GridUtils {
  public static final float GRID_SIZE = 1.0f; // 1 unit grid

  public static float snap(float value) {
    return Math.round(value / GRID_SIZE) * GRID_SIZE;
  }

  public static Vector3f snapVector(Vector3f rawPos) {
    return new Vector3f(
            snap(rawPos.x),
            rawPos.y,
            snap(rawPos.z)
    );
  }
}
