package org.bigacl.renderEngine;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
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
import static org.lwjgl.opengl.GL11.*;

public class GameLogic implements IGameLogic {
  private Camera camera;
  private WindowMaster window;
  private ShaderMaster shader;
  private ItemManger itemManger;
  private Mesh ground;
  private NanoVGUI gui;
  private int windowWidth = 1600;
  private int windowHeight = 900;

  public GameLogic(WindowMaster window, ShaderMaster shader, ItemManger itemManger) {
    this.window = window;
    this.shader = shader;
    this.itemManger = itemManger;
    this.camera = new Camera(1600,900);
    this.camera.setPosition(4.0f,4.0f,-3.25f,28.0f,-126.0f);
    this.ground = OBJLoader.loadOBJ("models/plane.obj");
    this.ground.setColor(0.004f, 0.05f, 0.0f);
    gui = new NanoVGUI();
    gui.init();
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
    //ground.render();
    shader.unbind();
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


    gui.beginFrame(windowWidth, windowHeight, 1.0f);

    // Draw HUD background
    gui.drawRoundedRect(10, 10, 200, 100, 5, 0.2f, 0.2f, 0.2f, 0.8f);

    // Draw text
    gui.drawText("Health: 100", 20, 40, 20, 1.0f, 1.0f, 1.0f);

    // Draw Nerd Font icons
    gui.drawNerdIcon("\uF004", 20, 70, 24, 1.0f, 0.0f, 0.0f); // Heart icon
    gui.drawNerdIcon("\uF005", 60, 70, 24, 0.0f, 1.0f, 0.0f); // Star icon

    gui.endFrame();
    glEnable(GL_DEPTH_TEST);
  }

  @Override
  public void cleanup() {
    // Clean up resources
  }

  public Camera getCamera() {
    return camera;
  }
}
