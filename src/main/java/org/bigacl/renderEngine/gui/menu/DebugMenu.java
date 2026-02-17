package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.design.BoxItems;
import org.bigacl.renderEngine.gui.text.TextItems;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;


public class DebugMenu {
  private final NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
  private boolean status = true;
  private BoxItems cameraInfo;

  public DebugMenu() {
    // Initialize inside the constructor to ensure ClassConst is populated
    this.cameraInfo = new BoxItems(new Vector3f(10, 10, 0), new Vector4f(0, 0, 0, 0.7f));
    createCameraDebug();
  }

  public void renderMenu() {
    if (!status) return; // Only render if toggled on!

    // Update before rendering
    updateCameraDebug();

    // Use a small offset so it's not glued to the very edge of the screen
    cameraInfo.renderAll(new Vector2f(10.0f, 10.0f));
  }

  private void updateCameraDebug() {
    Camera cam = ClassConst.camera;
    if (cam != null) {
      Vector3f pos = cam.getPosition();

      // Use String.format to handle that scientific notation comfortably
      // %.2f turns 4.000E+0 into a clean "4.00"

      cameraInfo.updateInfo(2, String.format("%.2f", pos.x));
      cameraInfo.updateInfo(3, String.format("%.2f", pos.y));
      cameraInfo.updateInfo(4, String.format("%.2f", pos.z));
      cameraInfo.updateInfo(6, String.format("%.2f", cam.getPitch()));
      cameraInfo.updateInfo(7, String.format("%.2f", cam.getYaw()));
    }
  }

  private void createCameraDebug() {
    cameraInfo.addItem(new TextItems(1, "Camera", "SYSTEM"));
    cameraInfo.addItem(new TextItems(2, "Position", ""));
    cameraInfo.addItem(new TextItems(0, "  X", "0")); // Index 2
    cameraInfo.addItem(new TextItems(0, "  Y", "0")); // Index 3
    cameraInfo.addItem(new TextItems(0, "  Z", "0")); // Index 4
    cameraInfo.addItem(new TextItems(2, "Rotation", ""));
    cameraInfo.addItem(new TextItems(0, "  Yaw", "0"));   // Index 6
    cameraInfo.addItem(new TextItems(0, "  Pitch", "0")); // Index 7
  }

  public boolean getStatus(){
    return status;
  }

  public void toggleStatus(){
    status = !status;
  }
}