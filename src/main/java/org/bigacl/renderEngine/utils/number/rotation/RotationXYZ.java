package org.bigacl.renderEngine.utils.number.rotation;

public class RotationXYZ {
  Rotation xValue;
  Rotation yValue;
  Rotation zValue;

  public RotationXYZ(Rotation xValue, Rotation yValue, Rotation zValue) {
    this.xValue = xValue;
    this.yValue = yValue;
    this.zValue = zValue;
  }

  public RotationXYZ(RotationFunction.CardinalDirection xValue, RotationFunction.CardinalDirection yValue, RotationFunction.CardinalDirection zValue) {
    this.xValue = new Rotation(xValue);
    this.yValue = new Rotation(yValue);
    this.zValue = new Rotation(zValue);
  }

  public RotationXYZ(Rotation defaultRotation) {
    this.xValue = new Rotation(defaultRotation.getRotation());
    this.yValue = new Rotation(defaultRotation.getRotation());
    this.zValue = new Rotation(defaultRotation.getRotation());
  }

  public RotationXYZ(RotationFunction.CardinalDirection defaultDirection) {
    this.xValue = new Rotation(defaultDirection);
    this.yValue = new Rotation(defaultDirection);
    this.zValue = new Rotation(defaultDirection);
  }


  public void setXValue(int degree) {
    this.xValue.set(degree);
  }

  public void setYValue(int degree) {
    this.yValue.set(degree);
  }

  public void setZValue(int degree) {
    this.zValue.set(degree);
  }

  public void setXValue(RotationFunction.CardinalDirection cardinal) {
    this.xValue.set(cardinal);
  }

  public void setYValue(RotationFunction.CardinalDirection cardinal) {
    this.yValue.set(cardinal);
  }

  public void setZValue(RotationFunction.CardinalDirection cardinal) {
    this.zValue.set(cardinal);
  }
}
