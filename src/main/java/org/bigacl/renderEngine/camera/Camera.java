package org.bigacl.renderEngine.camera;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
  private final Vector3f position;
  private float pitch;
  private float yaw;

  private final Matrix4f viewMatrix;
  private final Matrix4f projectionMatrix;

  // Player "Hitbox" dimensions
  private final float PLAYER_WIDTH = 0.6f;
  private final float PLAYER_HEIGHT = 1.8f;

  // Frustum Variables
  private FrustumIntersection frustum;
  private Matrix4f pvMatrix = new Matrix4f();

  public Camera(int width, int height) {
    position = new Vector3f(0, 0, 5);
    pitch = 0;
    yaw = 0;
    viewMatrix = new Matrix4f();
    projectionMatrix = new Matrix4f();

    projectionMatrix.perspective(
            (float) Math.toRadians(70.0f),
            (float) width / height,
            0.1f,
            100.0f
    );


    this.frustum = new FrustumIntersection();
    updateViewMatrix();
  }

  // --- COLLISION LOGIC ---

  /**
   * Validates if a move is possible by checking intersections with placed items.
   */
  private boolean canMoveTo(Vector3f targetPos, List<? extends BasePlaceableItem> items) {
    if (items == null || items.isEmpty()) return true;
    // Create a hitbox for the camera
    BasePlaceableItem.XYZMatrix camSize = new BasePlaceableItem.XYZMatrix();
    camSize.x = 0.6f; camSize.z = 1.8f; camSize.y = 0.6f;

    BoundingBox playerHitbox = new BoundingBox(targetPos, camSize);

    for (BasePlaceableItem item : items) {
      // IMPORTANT: The item must be placed and have a bounding box
      if (item.getBoundingBox() != null) {
        if (playerHitbox.intersects(targetPos, item.getBoundingBox(),item.getWorldPosition())) {
          return false; // Found a hit!
        }
      }
    }
    return true;
  }

  // --- UPDATED MOVEMENT METHODS ---

  public void moveForward(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position);
    nextPos.x += (float) Math.sin(Math.toRadians(yaw)) * amount;
    nextPos.z -= (float) Math.cos(Math.toRadians(yaw)) * amount;

    if (canMoveTo(nextPos, items)) {
      position.set(nextPos);
      updateViewMatrix();
    }
  }

  public void moveBackward(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position);
    nextPos.x -= (float) Math.sin(Math.toRadians(yaw)) * amount;
    nextPos.z += (float) Math.cos(Math.toRadians(yaw)) * amount;

    if (canMoveTo(nextPos, items)) {
      position.set(nextPos);
      updateViewMatrix();
    }
  }

  public void moveLeft(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position);
    nextPos.x -= (float) Math.cos(Math.toRadians(yaw)) * amount;
    nextPos.z -= (float) Math.sin(Math.toRadians(yaw)) * amount;

    if (canMoveTo(nextPos, items)) {
      position.set(nextPos);
      updateViewMatrix();
    }
  }

  public void moveRight(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position);
    nextPos.x += (float) Math.cos(Math.toRadians(yaw)) * amount;
    nextPos.z += (float) Math.sin(Math.toRadians(yaw)) * amount;

    if (canMoveTo(nextPos, items)) {
      position.set(nextPos);
      updateViewMatrix();
    }
  }

  // Space/Shift usually fly or jump; check Y axis collision too
  public void moveUp(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position).add(0, amount, 0);
    if (canMoveTo(nextPos, items)) {
      position.y += amount;
      updateViewMatrix();
    }
  }

  public void moveDown(float amount, List<? extends BasePlaceableItem> items) {
    Vector3f nextPos = new Vector3f(position).add(0, -amount, 0);
    if (canMoveTo(nextPos, items)) {
      position.y -= amount;
      updateViewMatrix();
    }
  }

  // --- INPUT HANDLING ---

  public void CameraInput(WindowMaster window, float moveSpeed, float rotateSpeed, List<? extends BasePlaceableItem> items){
    if (window.isKeyPressed(GLFW_KEY_W)) this.moveForward(moveSpeed, items);
    if (window.isKeyPressed(GLFW_KEY_S)) this.moveBackward(moveSpeed, items);
    if (window.isKeyPressed(GLFW_KEY_A)) this.moveLeft(moveSpeed, items);
    if (window.isKeyPressed(GLFW_KEY_D)) this.moveRight(moveSpeed, items);
    if (window.isKeyPressed(GLFW_KEY_SPACE)) this.moveUp(moveSpeed, items);
    if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) this.moveDown(moveSpeed, items);

    // Rotation (No collision needed for looking)
    if (window.isKeyPressed(GLFW_KEY_UP)) this.addRotation(-rotateSpeed, 0);
    if (window.isKeyPressed(GLFW_KEY_DOWN)) this.addRotation(rotateSpeed, 0);
    if (window.isKeyPressed(GLFW_KEY_LEFT)) this.addRotation(0, -rotateSpeed);
    if (window.isKeyPressed(GLFW_KEY_RIGHT)) this.addRotation(0, rotateSpeed);
  }

  // ... (rest of your existing methods like addRotation, updateViewMatrix, etc.)

  public void addRotation(float pitchChange, float yawChange) {
    pitch += pitchChange;
    yaw += yawChange;
    updateViewMatrix();
  }

  private void updateViewMatrix() {
    checkPitchAndYawAmount();

    // Calculate 80% height for the camera "eyes"
    float eyeHeight = PLAYER_HEIGHT * 0.8f;

    viewMatrix.identity()
            .rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0))
            .rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0))
            // Translate by position + the eye height offset
            .translate(-position.x, -(position.y + eyeHeight), -position.z);

    updateFrustum();
  }

  private void checkPitchAndYawAmount() {
    pitch = pitch % 360;
    yaw = yaw % 360;
  }

  public Matrix4f getViewMatrix() { return viewMatrix; }
  public Matrix4f getProjectionMatrix() { return projectionMatrix; }
  public Vector3f getPosition() { return position; }

  public void setPosition(float x, float y, float z, float pitch, float yaw) {
    this.position.x = x;
    this.position.y = y;
    this.position.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
    updateViewMatrix();
  }
  public void updateFrustum() {
    // Multiply Projection * View to get the "Clip Space" matrix
    pvMatrix.set(getProjectionMatrix()).mul(getViewMatrix());
    // Extract the 6 planes from the matrix
    frustum.set(pvMatrix);
  }
  public FrustumIntersection getFrustum() {
    return frustum;
  }
}