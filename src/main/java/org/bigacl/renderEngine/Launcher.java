package org.bigacl.renderEngine;

import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.player.level.MainLevel;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;

public class Launcher {
  private static  ShaderMaster shaderMaster;
  private static final MainLevel mainLevel = new MainLevel();
  public static void main(String[] args) {
    // Window Init
    WindowMaster window = new WindowMaster();
    window.init();

    // Shader Init
    shaderMaster = new ShaderMaster("shaders/vertex.glsl", "shaders/fragment.glsl");
    House house = new House();

    // Item Manger Iit
    ItemManger itemManger = new ItemManger();
    itemManger.addHouse(house);
    NanoVGUI nanoVGUI = new NanoVGUI();


    IGameLogic gameLogic = null;
    new ClassConst(gameLogic, shaderMaster, nanoVGUI, itemManger, window);
    gameLogic = new MainGame();
    NanoVGUI NanoVGUI = new NanoVGUI();
    // Render loop
    window.loop(gameLogic);

    // Cleanup
    house.cleanup();
    shaderMaster.cleanup();
    window.cleanup();
  }


  public static ShaderMaster getShaderMaster() {
    return shaderMaster;
  }

  public static MainLevel getMainLevel() {
    return mainLevel;
  }
}