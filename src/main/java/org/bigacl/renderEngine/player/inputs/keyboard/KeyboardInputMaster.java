package org.bigacl.renderEngine.player.inputs.keyboard;

import org.bigacl.renderEngine.player.camera.Camera;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gameItems.item.ItemManger;
import org.bigacl.renderEngine.gameItems.item.grid.GridUtils;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.player.inputs.mouse.MouseRayCast;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.timer.ClickTimer;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;

public class KeyboardInputMaster {
  private WindowMaster window;
  private ItemManger itemManger;
  private HudAbstract hudAbstract;
  private Camera camera;
  private double currentTime;

  private final ClickTimer placeItemTimer = new ClickTimer(1.0f);
  private boolean lastDebugKeyStatus = false;

  public KeyboardInputMaster() {
    updateVariables();
  }

  public void updateVariables() {
    this.window = ClassConst.window;
    this.itemManger = ClassConst.itemManger;
    this.hudAbstract = ClassConst.hudAbstract;
    this.camera = ClassConst.camera;
  }

  public void updateHudAbstract() {
    this.hudAbstract = ClassConst.hudAbstract;
  }

  public void input(double currentTime, Player player) {
    this.currentTime = currentTime;
    float moveSpeed = 0.02f;
    float rotateSpeed = 2.0f;
    if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) moveSpeed *= 10;
    if (window.isKeyPressed(GLFW_KEY_O)) player.addExperience(1);
    if (window.isKeyPressed(GLFW_KEY_H)) {
      if (placeItemTimer.checkTimer(currentTime)) {
        Vector3f hitPoint = MouseRayCast.getRaycastPosition();
        assert hitPoint != null;
        // 1. Snap the hit point to your grid
        Vector3f snappedPos = GridUtils.snapVector(hitPoint);
        // 2. Set the house position (using your Blender-to-Java logic)
        // Note: Since we are placing on the floor, Y is usually 0.
        House house = new House();
        house.setWorldPosition(snappedPos);
        // 3. Add to manager
        itemManger.addItem(house);
      }
    }
    camera.CameraInput(window, moveSpeed, rotateSpeed, this.itemManger.getAllItems());
    hudInputs();
  }
  private void hudInputs() {
    if (this.hudAbstract == null) {
      this.hudAbstract = ClassConst.hudAbstract;
      if (this.hudAbstract == null) return;
    }
    boolean currentDebugKeyStatus = window.isKeyPressed(GLFW_KEY_F3);
    if (currentDebugKeyStatus && !lastDebugKeyStatus) {
      this.hudAbstract.toggleDebugStatus();
    }
    this.lastDebugKeyStatus = currentDebugKeyStatus;
    this.hudAbstract.checkHudInputs();
  }

}
