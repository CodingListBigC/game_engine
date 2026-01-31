package org.bigacl.renderEngine.camera;

import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class Camera {
  private Vector3f position;
  private float pitch;  // Rotation around X axis (up/down)
  private float yaw;    // Rotation around Y axis (left/right)

  private Matrix4f viewMatrix;
  private Matrix4f projectionMatrix;

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

    updateViewMatrix();
  }

  public void moveUp(float amount) {
    position.y += amount;
    updateViewMatrix();
  }

  public void moveDown(float amount) {
    position.y -= amount;
    updateViewMatrix();
  }

  public void moveForward(float amount) {
    position.x += (float) Math.sin(Math.toRadians(yaw)) * amount;
    position.z -= (float) Math.cos(Math.toRadians(yaw)) * amount;
    updateViewMatrix();
  }

  public void moveBackward(float amount) {
    position.x -= (float) Math.sin(Math.toRadians(yaw)) * amount;
    position.z += (float) Math.cos(Math.toRadians(yaw)) * amount;
    updateViewMatrix();
  }

  public void moveLeft(float amount) {
    position.x -= (float) Math.cos(Math.toRadians(yaw)) * amount;
    position.z -= (float) Math.sin(Math.toRadians(yaw)) * amount;
    updateViewMatrix();
  }

  public void moveRight(float amount) {
    position.x += (float) Math.cos(Math.toRadians(yaw)) * amount;
    position.z += (float) Math.sin(Math.toRadians(yaw)) * amount;
    updateViewMatrix();
  }

  public void addRotation(float pitchChange, float yawChange) {
    pitch += pitchChange;
    yaw += yawChange;
    updateViewMatrix();
  }

  private void updateViewMatrix() {
    viewMatrix.identity();
    viewMatrix.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0));
    viewMatrix.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0));
    viewMatrix.translate(-position.x, -position.y, -position.z);
  }

  public Matrix4f getViewMatrix() {
    return viewMatrix;
  }

  public Matrix4f getProjectionMatrix() {
    return projectionMatrix;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void CameraInput(WindowMaster window, float moveSpeed, float rotateSpeed){

    if (window.isKeyPressed(GLFW_KEY_W)) {
      this.moveForward(moveSpeed);
    }
    if (window.isKeyPressed(GLFW_KEY_S)) {
      this.moveBackward(moveSpeed);
    }
    if (window.isKeyPressed(GLFW_KEY_A)) {
      this.moveLeft(moveSpeed);
    }
    if (window.isKeyPressed(GLFW_KEY_D)) {
      this.moveRight(moveSpeed);
    }
    if (window.isKeyPressed(GLFW_KEY_SPACE)) {
      this.moveUp(moveSpeed);
    }
    if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
      this.moveDown(moveSpeed);
    }

    // Rotation
    if (window.isKeyPressed(GLFW_KEY_UP)) {
      this.addRotation(-rotateSpeed, 0);  // Look up
    }
    if (window.isKeyPressed(GLFW_KEY_DOWN)) {
      this.addRotation(rotateSpeed, 0);   // Look down
    }
    if (window.isKeyPressed(GLFW_KEY_LEFT)) {
      this.addRotation(0, -rotateSpeed);  // Look left
    }
    if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
      this.addRotation(0, rotateSpeed);   // Look right
    }
  }

  public void setPosition(float x, float y, float z, float pitch, float yaw){
    position.x = x;
    position.y = y;
    position.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
    updateViewMatrix();
  }

  public void printPosition() {
    System.out.println("Camera Location, x: "+ position.x + ", y: " + position.y + ", z: " + position.z + ". pitch" + pitch + ", yaw: " + yaw );
  }
}