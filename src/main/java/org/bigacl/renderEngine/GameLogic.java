package org.bigacl.renderEngine;

import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class GameLogic implements IGameLogic {
  private Camera camera;
  private WindowMaster window;
  private ShaderMaster shader;
  private Mesh mesh;

  public GameLogic(WindowMaster window, ShaderMaster shader, Mesh mesh) {
    this.window = window;
    this.shader = shader;
    this.mesh = mesh;
    this.camera = new Camera(1600,900);
  }

  @Override
  public void input() {
    float moveSpeed = 0.02f;
    float rotateSpeed = 2.0f;
    camera.CameraInput(window, moveSpeed, rotateSpeed);

    if (window.isKeyPressed(GLFW_KEY_1)){
      mesh.setColor(0.0f,0.0f,0.0f);
    }
    if (window.isKeyPressed(GLFW_KEY_2)){
      mesh.setColor(1.0f,0.0f,0.0f);
    }
    if (window.isKeyPressed(GLFW_KEY_3)){
      mesh.setColor(0.0f,1.0f,0.0f);
    }
    if (window.isKeyPressed(GLFW_KEY_4)){
      mesh.setColor(0.0f,0.0f,1.0f);
    }
  }
  @Override
  public void update(float delta) {
    // Update game objects (physics, AI, etc)
    // delta helps make movement frame-rate independent
  }

  @Override
  public void render() {
    // Render your scene
    shader.bind();
    shader.setUniformMatrix4f("projection", camera.getProjectionMatrix());
    shader.setUniformMatrix4f("view", camera.getViewMatrix());
    Matrix4f modelMatrix = new Matrix4f().identity();
    shader.setUniformMatrix4f("model", modelMatrix);
    mesh.render();
    shader.unbind();
  }

  @Override
  public void cleanup() {
    // Clean up resources
  }
}
