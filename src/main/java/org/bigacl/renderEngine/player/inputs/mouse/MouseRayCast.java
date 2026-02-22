package org.bigacl.renderEngine.player.inputs.mouse;

import org.bigacl.renderEngine.player.camera.Camera;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MouseRayCast {

  /**
   * Finds the 3D point on the floor (Y = 0) where the mouse is pointing.
   */
  public static Vector3f getRaycastPosition() {
    WindowMaster win = ClassConst.window;
    Camera cam = ClassConst.camera;

    // 1. Get current mouse direction
    Vector3f rayDir = calculateRayDirection(win.getMouseX(), win.getMouseY(), win, cam);
    Vector3f camPos = cam.getPosition();

    // 2. Intersect with the floor plane (Y = 0)
    // Formula: t = (targetY - camPos.y) / rayDir.y
    if (Math.abs(rayDir.y) > 0.0001f) {
      float t = -camPos.y / rayDir.y;

      // If t > 0, the floor is in front of us
      if (t > 0) {
        Vector3f hitPoint = new Vector3f(rayDir).mul(t).add(camPos);
        return hitPoint;
      }
    }

    // Fallback: If looking at the sky, return far away or null
    return new Vector3f(0, 0, 0);
  }

  /**
   * Converts 2D screen coordinates to a 3D unit vector direction.
   */
  private static Vector3f calculateRayDirection(float mouseX, float mouseY, WindowMaster win, Camera cam) {
    // 1. Normalized Device Coordinates (-1 to 1)
    float x = (2.0f * mouseX) / win.getWidth() - 1.0f;
    float y = 1.0f - (2.0f * mouseY) / win.getHeight();

    // 2. Clip Space
    Vector4f rayClip = new Vector4f(x, y, -1.0f, 1.0f);

    // 3. Eye Space (Undo Projection)
    Matrix4f invProjection = new Matrix4f(cam.getProjectionMatrix()).invert();
    invProjection.transform(rayClip);
    // We only care about direction, so set Z to forward and W to 0
    rayClip.z = -1.0f;
    rayClip.w = 0.0f;

    // 4. World Space (Undo View/Camera Rotation)
    Matrix4f invView = new Matrix4f(cam.getViewMatrix()).invert();
    Vector4f rayWorld4 = invView.transform(rayClip);

    Vector3f rayWorld = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z);
    return rayWorld.normalize();
}
}