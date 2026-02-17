package org.bigacl.renderEngine.camera;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.utils.consts.ClassConst;
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

  // Player dimensions
  private final float PLAYER_WIDTH = 0.6f;
  private final float PLAYER_DEPTH = 0.6f;
  private final float PLAYER_HEIGHT = 1.8f;

  // Frustum and Matrix calculations
  private final FrustumIntersection frustum;
  private final Matrix4f pvMatrix = new Matrix4f();

  public Camera(int width, int height) {
    // Start position (Feet level)
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

  public Camera() {
    this(ClassConst.window.getWidth(), ClassConst.window.getHeight());
  }

  /**
   * Checks if the player's hitbox would intersect any items at the target position.
   */
  private boolean canMoveTo(Vector3f targetPos, List<? extends BasePlaceableItem> items) {
    if (items == null || items.isEmpty()) return true;

    // Define player size for the hitbox (X=Width, Y=Height, Z=Depth)
    BasePlaceableItem.XYZMatrix playerSize = new BasePlaceableItem.XYZMatrix();
    playerSize.x = PLAYER_WIDTH; // Width
    playerSize.y = PLAYER_DEPTH; // Depth
    playerSize.z = PLAYER_HEIGHT; // Height

    // Create a world-space hitbox for the player at the proposed location
    BoundingBox playerHitbox = new BoundingBox(targetPos, playerSize);

    for (BasePlaceableItem item : items) {
      // Get the item's current world-space bounding box
      BoundingBox itemWorldBox = item.getBoundingBoxOffSet();
      if (itemWorldBox != null) {
        // DEBUG: Only print when close to the building to avoid spam
        if (targetPos.distance(item.getWorldPosition()) < 3.0f) {
          System.out.println("Checking Collision: PlayerX(" + playerHitbox.minX + ") vs ItemX(" + itemWorldBox.minX + ")");
        }

        if (playerHitbox.intersects(itemWorldBox)) {
          System.out.println("!!! COLLISION DETECTED !!!");
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Handles movement input and checks for collisions on X and Z axes independently.
   * This allows for "sliding" against walls.
   */
  public void CameraInput(WindowMaster window, float moveSpeed, float rotateSpeed, List<? extends BasePlaceableItem> items) {
    float dx = 0;
    float dz = 0;
    float dy = 0;

    // 1. Calculate the INTENDED movement (relative to where you are looking)
    if (window.isKeyPressed(GLFW_KEY_W)) {
      dx += (float) Math.sin(Math.toRadians(yaw)) * moveSpeed;
      dz -= (float) Math.cos(Math.toRadians(yaw)) * moveSpeed;
    }
    if (window.isKeyPressed(GLFW_KEY_S)) {
      dx -= (float) Math.sin(Math.toRadians(yaw)) * moveSpeed;
      dz += (float) Math.cos(Math.toRadians(yaw)) * moveSpeed;
    }
    if (window.isKeyPressed(GLFW_KEY_A)) {
      dx -= (float) Math.cos(Math.toRadians(yaw)) * moveSpeed;
      dz -= (float) Math.sin(Math.toRadians(yaw)) * moveSpeed;
    }
    if (window.isKeyPressed(GLFW_KEY_D)) {
      dx += (float) Math.cos(Math.toRadians(yaw)) * moveSpeed;
      dz += (float) Math.sin(Math.toRadians(yaw)) * moveSpeed;
    }
    if (window.isKeyPressed(GLFW_KEY_SPACE)){
      dy += (float) moveSpeed;
    }
    if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
      dy -= (float) moveSpeed;
    }

    // 2. CHECK X AXIS
    if (dx != 0) {
      Vector3f nextX = new Vector3f(position.x + dx, position.y, position.z);
      if (canMoveTo(nextX, items)) {
        position.x += dx; // ONLY move if allowed
      }
    }


    // 3. CHECK Z AXIS
    if (dz != 0) {
      Vector3f nextZ = new Vector3f(position.x, position.y, position.z + dz);
      if (canMoveTo(nextZ, items)) {
        position.z += dz; // ONLY move if allowed
      }
    }

    // 3. CHECK Y AXIS
    if (dy != 0) {
      Vector3f nextX = new Vector3f(position.x + dx, position.y, position.z);
      if (canMoveTo(nextX, items)) {
        position.y += dy; // ONLY move if allowed
      }
    }


    // Rotation (Always allowed)
    if (window.isKeyPressed(GLFW_KEY_LEFT)) this.addRotation(0, -rotateSpeed);
    if (window.isKeyPressed(GLFW_KEY_RIGHT)) this.addRotation(0, rotateSpeed);
    if (window.isKeyPressed(GLFW_KEY_UP)) this.addRotation(-rotateSpeed,0);
    if (window.isKeyPressed(GLFW_KEY_DOWN)) this.addRotation(rotateSpeed,0);

    updateViewMatrix();
  }

  public void addRotation(float pitchChange, float yawChange) {
    pitch += pitchChange;
    yaw += yawChange;

    // Clamp pitch to prevent flipping
    if (pitch > 89.0f) pitch = 89.0f;
    if (pitch < -89.0f) pitch = -89.0f;

    updateViewMatrix();
  }

  private void updateViewMatrix() {
    pitch = pitch % 360;
    yaw = yaw % 360;

    // Camera "eyes" are usually near the top of the player height
    float eyeHeight = PLAYER_HEIGHT * 0.85f;

    viewMatrix.identity()
            .rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0))
            .rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0))
            .translate(-position.x, -(position.y + eyeHeight), -position.z);

    updateFrustum();
  }

  public void updateFrustum() {
    pvMatrix.set(projectionMatrix).mul(viewMatrix);
    frustum.set(pvMatrix);
  }

  // Getters
  public Matrix4f getViewMatrix() { return viewMatrix; }
  public Matrix4f getProjectionMatrix() { return projectionMatrix; }
  public Vector3f getPosition() { return position; }
  public FrustumIntersection getFrustum() { return frustum; }

  public void setPosition(float x, float y, float z, float pitch, float yaw) {
    this.position.set(x, y, z);
    this.pitch = pitch;
    this.yaw = yaw;
    updateViewMatrix();
  }

  public float getPitch() {
    return pitch;
  }

  public float getYaw() {
    return yaw;
  }
}