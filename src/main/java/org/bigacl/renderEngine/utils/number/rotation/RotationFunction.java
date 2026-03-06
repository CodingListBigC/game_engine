package org.bigacl.renderEngine.utils.number.rotation;

public class RotationFunction {
  public enum CardinalDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST,
  }

  public static int getRotationInt(CardinalDirection direction) {
    return direction.ordinal() * 45;
  }

  public static CardinalDirection getCardinalDirection(double rotation) {
    double normalized = (rotation % 360 + 360) % 360;

    // 2. Shift by 22.5 degrees so that North (0) is centered in its 45-degree slice
    // 3. Divide by 45 (the width of one cardinal/intercardinal direction)
    int index = (int) ((normalized + 22.5) / 45) % 8;

    // This assumes your Enum is ordered: N, NE, E, SE, S, SW, W, NW
    return CardinalDirection.values()[index];
  }
}
