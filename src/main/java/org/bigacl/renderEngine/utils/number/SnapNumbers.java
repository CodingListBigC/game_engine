package org.bigacl.renderEngine.utils.number;

public class SnapNumbers {
  float changeAmount;
  float currentAmount;

  public SnapNumbers(float changeAmount, float currentAmount) {
    this.changeAmount = changeAmount;
    this.currentAmount = currentAmount;
  }

  public void addChangeAmount() {
    this.currentAmount += changeAmount;
  }

  public void subtractChangeAmount() {
    this.currentAmount -= changeAmount;
  }

  public float getChangeAmount() {
    return changeAmount;
  }

  public void setChangeAmount(float changeAmount) {
    this.changeAmount = changeAmount;
  }

  public float getCurrentAmount() {
    return currentAmount;
  }

  public void setCurrentAmount(float currentAmount) {
    this.currentAmount = currentAmount;
  }

  public void changeByInt(int changeInt) {
    this.currentAmount += this.changeAmount * changeInt;

  }
}
