package org.bigacl.renderEngine.utils.number.rotation;

public class Rotation {
  double degree;
  RotationFunction.CardinalDirection cardinal;

  public Rotation(double degree) {
    this.degree = degree;
    this.cardinal = RotationFunction.getCardinalDirection(this.degree);
  }

  public Rotation(RotationFunction.CardinalDirection cardinal) {
    this.cardinal = cardinal;
    this.degree = RotationFunction.getRotationInt(cardinal);
  }

  public double getRotation() {
    return this.degree;
  }

  public void set(double degree) {
    this.degree = degree;
    this.cardinal = RotationFunction.getCardinalDirection(this.degree);
  }

  public void set(RotationFunction.CardinalDirection cardinal) {
    this.cardinal = cardinal;
    this.degree = RotationFunction.getRotationInt(cardinal);
  }

  public void checkDegree() {
    this.degree = this.degree % 360.0f;

    // 2. Handle negative inputs (e.g., -10 becomes 350)
    if (this.degree < 0) {
      this.degree += 360.0f;
    }
  }
}
