package org.bigacl.renderEngine.utils.consts;

import org.bigacl.renderEngine.gameItems.item.ItemMangerAbstract;
import org.bigacl.renderEngine.gameItems.item.tool.ItemMangerModelDesign;
import org.bigacl.renderEngine.gameManger.MainGame;
import org.bigacl.renderEngine.gameManger.ModelDesignLogic;
import org.bigacl.renderEngine.player.camera.Camera;
import org.bigacl.renderEngine.gui.font.FontSizing;
import org.bigacl.renderEngine.gui.drawing.NanoVGUI;
import org.bigacl.renderEngine.gui.menu.hudMenu.MasterGameHud;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.MasterHud;
import org.bigacl.renderEngine.gameItems.item.ItemMangerMainGame;
import org.bigacl.renderEngine.gameManger.MainLogic;
import org.bigacl.renderEngine.player.level.MainLevel;
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
  public static MainLogic game;
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
  public static ItemMangerAbstract<?, ?> itemMangerAbstract;
  /**
   * The GLFW window and input handler
   */
  public static WindowMaster window;

  /**
   * The Main Camera for game
   */
  public static Camera camera;

  /**
   * Default Size
   */
  public static FontSizing fontSizing;
  public static MainLevel mainLevel;
  public static HudAbstract hudAbstract;
  public static MainLogic mainLogic;

  static {
    window = new WindowMaster();
    window.init();

    camera = new Camera();
    shader3d = new ShaderMaster();
    nanoVGUI = new NanoVGUI();
    itemMangerAbstract = new ItemMangerMainGame();
    fontSizing = new FontSizing(16);
    mainLevel = new MainLevel();
    game = new MainGame();
  }

  public static void setHudAbstract() {
    if (hudAbstract !=null){
      return;
    }
    if (Const.hudMode == 1) {
      hudAbstract = new MasterHud();
      mainLogic = new ModelDesignLogic();
      itemMangerAbstract = new ItemMangerModelDesign();
    } else {
      hudAbstract = new MasterGameHud();
    }
  }
  public static void setHudAbstract(int changeHudMod){
    Const.hudMode = changeHudMod;
    setHudAbstract();
  }

  public static void setIGameLogic(MainLogic mainLogicInput) {
    mainLogic = mainLogicInput;

  }

  public static void cleanup() {
    // Clean up functions
    itemMangerAbstract.cleanupAll();
    shader3d.cleanup();
    window.cleanup();

  }
}