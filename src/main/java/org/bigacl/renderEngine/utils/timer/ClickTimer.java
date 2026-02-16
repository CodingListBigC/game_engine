package org.bigacl.renderEngine.utils.timer;

public class ClickTimer {
  private final float PLACEMENT_COOLDOWN;
  private double lastPlacementTime = 0;
  public ClickTimer(float PLACEMENT_COOLDOWN) {
    this.PLACEMENT_COOLDOWN = PLACEMENT_COOLDOWN;
  }

  public boolean checkTimer(double currentTime){
    if (currentTime - lastPlacementTime >=PLACEMENT_COOLDOWN){
      lastPlacementTime = currentTime;
      return true;
    }
    return false;
  }
}
