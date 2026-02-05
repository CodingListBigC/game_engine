package org.bigacl.renderEngine.utils.consts;

import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.window.WindowMaster;

/**
 * Global constant container for core engine systems.
 * This prevents "constructor bloating" by grouping main managers together.
 */
public class ClassConst {
  /**
   * The main game state and logic controller
   */
  public static IGameLogic game;
  /**
   * The primary shader provider for 3D world rendering
   */
  public static ShaderMaster shader3d;
  /**
   * The 2D vector graphics wrapper for UI (NanoVG)
   */
  public static NanoVGUI nanoVGUI;
  /**
   * Manager handling all placeable items, houses, and models
   */
  public static ItemManger itemManger;
  /**
   * The GLFW window and input handler
   */
  public static WindowMaster window;

  /**
   *  The Main Camera for game
   */
  public static Camera camera;
  /**
   *
   * @param game
   * @param shader3d
   * @param nanoVGUI
   * @param itemManger
   * @param window
   * @param camera
   */
  public ClassConst(IGameLogic game, ShaderMaster shader3d, NanoVGUI nanoVGUI, ItemManger itemManger, WindowMaster window, Camera camera) {
    ClassConst.game = game;
    ClassConst.shader3d = shader3d;
    ClassConst.nanoVGUI = nanoVGUI;
    ClassConst.itemManger = itemManger;
    ClassConst.window = window;
    ClassConst.camera = camera;
  }
}