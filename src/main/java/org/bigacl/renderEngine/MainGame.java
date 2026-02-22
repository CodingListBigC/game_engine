package org.bigacl.renderEngine;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.grid.GridUtils;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.model.DefaultModelFunctions;
import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.player.inputs.mouse.MouseRayCast;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.timer.ClickTimer;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Mangers the whole main game.
 */
public class MainGame implements IGameLogic {
  private final Camera camera;
  private final WindowMaster window;
  private final ShaderMaster shader3d;
  private final ItemManger itemManger;
  private final Mesh ground;

  // Player Variables
  private final Player player;

  private final ClickTimer placeItemTimer = new ClickTimer(1.0f);
  private final ClickTimer debugInputTimer = new ClickTimer(1.0f);

  // HUD Variables;
  private final NanoVGUI gui;
  private HudAbstract hudAbstract;

  public MainGame() {
    ClassConst.setIGameLogic(this);
    this.window = ClassConst.window;
    this.shader3d = ClassConst.shader3d;
    this.itemManger = ClassConst.itemManger;
    this.camera = ClassConst.camera;
    this.camera.setFullPositionReset(0.0f, 4.0f, 3.25f, -50.0f, 0.0f);
    this.ground = OBJLoader.loadOBJ("models/plane.obj");
    this.ground.setColor(0.004f, 0.05f, 0.0f);
    // HUd setup
    this.gui = ClassConst.nanoVGUI;
    this.gui.init();
    this.hudAbstract = ClassConst.hudAbstract;
    ;

    //Player Setup
    this.player = new Player("Bigacl", "Christian", new Vector3f(1.0f, 0.0f, 0.0f));
  }

  @Override
  public void input() {
    double currentTime = glfwGetTime();
    float moveSpeed = 0.02f;
    float rotateSpeed = 2.0f;
    if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
      moveSpeed = 0.02f * 10;
    }
    if (window.isKeyPressed(GLFW_KEY_O)) {
      player.addExperience(1);
    }
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
    // TOOD: Add hud inputs
    camera.CameraInput(window, moveSpeed, rotateSpeed, this.itemManger.getAllItems());
    if (hudAbstract != null) {
      hudAbstract.checkHudInputs();
    }
  }

  @Override
  public void update(float delta) {
    // Update game objects (physics, AI, etc)
    // delta helps make movement frame-rate independent
  }

  public void updateHudAbstract() {
    this.hudAbstract = ClassConst.hudAbstract;
  }

  @Override
  public void render() {
    // Render your scene
    shader3d.bind();
    shader3d.setUniformMatrix4f("projection", camera.getProjectionMatrix());
    shader3d.setUniformMatrix4f("view", camera.getViewMatrix());
    Matrix4f modelMatrix = new Matrix4f().identity();
    shader3d.setUniformMatrix4f("model", modelMatrix);
    render3dModels();
    shader3d.unbind();

    glBindTexture(GL_TEXTURE_2D, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glUseProgram(0);
    glDisable(GL_DEPTH_TEST);


    int windowWidth = window.getWidth();
    int windowHeight = window.getHeight();
    gui.beginFrame(windowWidth, windowHeight, 1.0f);
    renderHud();
    gui.endFrame();
    glEnable(GL_DEPTH_TEST);
  }

  @Override
  public void render3dModels() {
    itemManger.renderAll();
    ground.render(DefaultModelFunctions.getPlainModelMatrix(new Vector3f(0.0f),new Vector3f(0.0f)));
  }

  @Override
  public void renderHud() {
    if (this.hudAbstract == null) {
      initializeHud();
    }

    // Ensure we don't call render if initialization failed
    if (this.hudAbstract != null) {
      this.hudAbstract.render();
    }
  }

  private void initializeHud() {
    ClassConst.setHudAbstract();
    this.hudAbstract = ClassConst.hudAbstract;
  }

  @Override
  public void cleanup() {
    // Clean up resources
  }

  public Camera getCamera() {
    return camera;
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }
}
