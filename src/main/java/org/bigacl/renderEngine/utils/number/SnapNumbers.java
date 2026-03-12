package org.bigacl.renderEngine.utils.number;

public class SnapNumbers {
  float changeAmount;
  float currentAmount;
  float minValue = 1;
  float maxValue = -1;

  public SnapNumbers(float changeAmount, float currentAmount) {
    this.changeAmount = changeAmount;
    this.currentAmount = currentAmount;
  }

  public SnapNumbers(float changeAmount, float currentAmount, float minValue, float maxValue) {
    this.changeAmount = changeAmount;
    this.currentAmount = currentAmount;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  public void addChangeAmount() {
    this.currentAmount += changeAmount;
    checkValues();
  }

  public void subtractChangeAmount() {
    this.currentAmount -= changeAmount;
    checkValues();
  }

  public float getChangeAmount() {
    return changeAmount;
  }

  public void setChangeAmount(float changeAmount) {
    this.changeAmount = changeAmount;
    checkValues();
  }

  public float getCurrentAmount() {
    return currentAmount;
  }

  public void setCurrentAmount(float currentAmount) {
    this.currentAmount = currentAmount;
    checkValues();
  }

  public void changeByInt(int changeInt) {
    this.currentAmount += this.changeAmount * changeInt;
    checkValues();
  }

  private void checkValues() {
    if (this.currentAmount < minValue) this.currentAmount = minValue;
    if (this.changeAmount < minValue) this.changeAmount = minValue;
    if (this.maxValue == -1) return;
    if (this.maxValue <= this.currentAmount) this.currentAmount = this.maxValue;
    if (this.maxValue <= this.changeAmount) this.changeAmount = this.maxValue;
  }

}