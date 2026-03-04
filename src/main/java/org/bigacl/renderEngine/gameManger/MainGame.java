package org.bigacl.renderEngine.gameManger;

import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.model.mesh.OBJLoader;
import org.bigacl.renderEngine.model.DefaultModelFunctions;
import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.player.inputs.keyboard.KeyboardInputMaster;
import org.joml.Vector3f;

import static org.bigacl.renderEngine.utils.consts.ClassConst.*;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Mangers the whole main game.
 */
public class MainGame implements MainLogic {
  private final Mesh ground;

  // Player Variables
  private final Player player;


  // HUD Variables;

  // Inputs
  private final KeyboardInputMaster keyboardInputMaster;

  public MainGame() {
    ClassConst.setIGameLogic(this);
    camera.setFullPositionReset(0.0f, 4.0f, 3.25f, -50.0f, 0.0f);
    this.ground = OBJLoader.loadOBJ("models/plane.obj");
    this.ground.setColor(0.004f, 0.05f, 0.0f);
    // HUd setup
    nanoVGUI.init();
    hudAbstract = null;
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

  @Override
  public void render3dModels() {
    itemMangerAbstract.renderAll();
    ground.render(DefaultModelFunctions.getPlainModelMatrix(new Vector3f(0.0f),new Vector3f(0.0f)));
  }

}
