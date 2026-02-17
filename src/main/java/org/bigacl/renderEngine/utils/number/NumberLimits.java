package org.bigacl.renderEngine.utils.number;

public class NumberLimits {
  private int max;
  private int min;
  private int difference;
  private float currentValue;
  private boolean canLoop = true;

  public NumberLimits(int min, int max, int currentValue) {
    this.max = max;
    this.min = min;
    this.difference = this.max - this.min;
    this.currentValue = currentValue;
  }

  public NumberLimits(int min, int max, int currentValue, boolean canLoop) {
    this(min,max, currentValue);
    this.canLoop = canLoop;
  }
  public NumberLimits(int minAndMax, int currentValue){
    this(-minAndMax, minAndMax,currentValue);
  }
  public NumberLimits(int minAndMax, int currentValue, boolean canLoop){
    this(-minAndMax, minAndMax,currentValue);
    this.canLoop = canLoop;
  }

  /**
   * Set limits of number
   */
  public void setLimits(int max, int min) {
    this.max = max;
    this.min = min;
    this.difference = this.max - this.min;
  }

  public void add(float addBy) {
    this.currentValue += addBy;
    this.checkLimits();
  }

  public void subtract(float addBy) {
    this.currentValue -= addBy;
    this.checkLimits();
  }
  private void checkLimits() {
    if (this.canLoop) {
      checkLimitsLoop();
    } else {
      checkLimitsHard();
    }
  }

  private void checkLimitsLoop() {
    if (this.currentValue > this.max) {
      this.currentValue -= this.difference;
    } else if (this.currentValue < this.min) {
      this.currentValue += this.difference;
    }
  }

  private void checkLimitsHard() {
    if (this.currentValue > this.max) {
      this.currentValue = this.max;
    } else if (this.currentValue < this.min) {
      this.currentValue = this.min;
    }
  }

  public float getValue() {
    return currentValue;
  }

  public void setValue(int pitch) {
    this.currentValue = pitch;
    while (!(this.currentValue >= min && this.currentValue <= max)){
      checkLimits();
    }
  }
}
