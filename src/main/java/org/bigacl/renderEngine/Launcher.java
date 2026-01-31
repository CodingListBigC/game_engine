package org.bigacl.renderEngine;

import com.sun.tools.javac.Main;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.player.level.MainLevel;
import org.bigacl.renderEngine.shaders.ShaderMaster;
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

    // GameLogicMenu
    IGameLogic gameLogic = new GameLogic(window, shaderMaster, itemManger);
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