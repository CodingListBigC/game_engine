package org.bigacl.renderEngine.player.inputs.mouse;

import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class MouseRayCast {
  public Vector3f calculateMouseRay() {
    WindowMaster window = ClassConst.window;
    Camera camera = ClassConst.camera;
    float mouseX = window.getMouseX();
    float mouseY = window.getMouseY();
    int width = window.getWidth();
    int height = window.getHeight();

    // 1. Normalized Device Coordinates (-1 to 1)
    float x = (2.0f * mouseX) / width - 1.0f;
    float y = 1.0f - (2.0f * mouseY) / height;

    // 2. Ray in Clip Space
    Vector4f rayClip = new Vector4f(x, y, -1.0f, 1.0f);

    // 3. Ray in Eye Space (Inverse Projection)
    Matrix4f invProjection = new Matrix4f(camera.getProjectionMatrix()).invert();
    invProjection.transform(rayClip);
    rayClip.z = -1.0f; // Point forward
    rayClip.w = 0.0f;

    // 4. Ray in World Space (Inverse View)
    Matrix4f invView = new Matrix4f(camera.getViewMatrix()).invert();
    Vector4f rayWorld = invView.transform(rayClip);

    return new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z).normalize();
  }
  public static Vector3f getRaycastPosition() {
    WindowMaster window = ClassConst.window;
    Camera camera = ClassConst.camera;
    // 1. Get Mouse Coordinates
    float mouseX = window.getMouseX();
    float mouseY = window.getMouseY();
    int width = window.getWidth();
    int height = window.getHeight();

    // 2. Map to Normalized Device Coordinates (NDC) [-1 to 1]
    float x = (2.0f * mouseX) / width - 1.0f;
    float y = 1.0f - (2.0f * mouseY) / height;

    // 3. Clip Space -> Eye Space
    // We want a ray pointing forward, so we use z = -1.0
    Vector4f rayClip = new Vector4f(x, y, -1.0f, 1.0f);
    Matrix4f invProjection = new Matrix4f(camera.getProjectionMatrix()).invert();
    Vector4f rayEye = invProjection.transform(rayClip);
    rayEye.z = -1.0f;
    rayEye.w = 0.0f;

    // 4. Eye Space -> World Space
    Matrix4f invView = new Matrix4f(camera.getViewMatrix()).invert();
    Vector4f rayWorld4 = invView.transform(rayEye);
    Vector3f rayDir = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z).normalize();

    // 5. Ray-Plane Intersection (Intersection with the floor Y = 0)
    // Ray Equation: P = Origin + t * Direction
    // Since we want the floor (P.y = 0): 0 = Origin.y + t * Dir.y
    // Solve for t: t = -Origin.y / Dir.y
    Vector3f rayOrigin = new Vector3f(camera.getPosition());

    // We add the 80% eye height offset we set earlier to the origin
    float eyeHeight = 1.8f * 0.8f; // Assuming 1.8m player height
    rayOrigin.y += eyeHeight;

    if (Math.abs(rayDir.y) < 0.0001f) {
      return null; // Ray is parallel to the floor
    }

    float t = -rayOrigin.y / rayDir.y;

    if (t < 0) {
      return null; // The floor is behind the camera
    }

    // Return the final 3D point on the floor
    return new Vector3f(rayDir).mul(t).add(rayOrigin);
  }
}
