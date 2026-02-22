package org.bigacl.renderEngine.gameManger;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gameItems.item.ItemManger;
import org.bigacl.renderEngine.gameManger.logic.IGameLogic;
import org.bigacl.renderEngine.player.camera.Camera;
import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.model.mesh.OBJLoader;
import org.bigacl.renderEngine.model.DefaultModelFunctions;
import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.player.inputs.keyboard.KeyboardInputMaster;
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


  // HUD Variables;
  private final NanoVGUI gui;
  private HudAbstract hudAbstract;

  // Inputs
  private final KeyboardInputMaster keyboardInputMaster;

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
    // Input Setups
    this.keyboardInputMaster = new KeyboardInputMaster();

    //Player Setup
    this.player = new Player("Bigacl", "Christian", new Vector3f(1.0f, 0.0f, 0.0f));
  }

  @Override
  public void input() {
    double currentTime = glfwGetTime();
    keyboardInputMaster.input(currentTime, this.player);

  }

  @Override
  public void update(float delta) {
    // Update game objects (physics, AI, etc)
    // delta helps make movement frame-rate independent
  }

  public void updateHudAbstract() {
    this.hudAbstract = ClassConst.hudAbstract;
    keyboardInputMaster.updateHudAbstract();
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
