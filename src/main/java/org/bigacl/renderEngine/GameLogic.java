package org.bigacl.renderEngine;

import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.texture.Texture;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class GameLogic implements IGameLogic {
  private Camera camera;
  private WindowMaster window;
  private ShaderMaster shader;
  private ItemManger itemManger;
  private Mesh ground;

  public GameLogic(WindowMaster window, ShaderMaster shader, ItemManger itemManger) {
    this.window = window;
    this.shader = shader;
    this.itemManger = itemManger;
    this.camera = new Camera(1600,900);
    this.camera.setPosition(4.0f,4.0f,-3.25f,28.0f,-126.0f);
    this.ground = OBJLoader.loadOBJ("models/plane.obj");
    this.ground.setColor(0.004f, 0.05f, 0.0f);
  }

  @Override
  public void input() {
    float moveSpeed = 0.02f;
    float rotateSpeed = 2.0f;
    if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)){
      moveSpeed = 0.02f * 10;
    }
    if (window.isKeyPressed(GLFW_KEY_P)){
      camera.printPosition();
    }
    camera.CameraInput(window, moveSpeed, rotateSpeed);
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
    itemManger.renderAll();
    ground.render();
    shader.unbind();
  }

  @Override
  public void cleanup() {
    // Clean up resources
  }

  public Camera getCamera() {
    return camera;
  }
}
