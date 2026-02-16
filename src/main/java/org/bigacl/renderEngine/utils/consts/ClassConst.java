package org.bigacl.renderEngine.utils.consts;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.gui.font.FontSizing;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.MasterHud;
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
   * Default Size
   */
  public static FontSizing fontSizing;
  public static MasterHud masterHud;
  static {
    window = new WindowMaster();
    window.init();

    camera = new Camera();
    shader3d = new ShaderMaster();
    nanoVGUI = new NanoVGUI();
    itemManger = new ItemManger();
    fontSizing = new FontSizing(16);

    game = new MainGame();

    masterHud = new MasterHud(game);
  }
}