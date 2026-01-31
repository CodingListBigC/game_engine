package org.bigacl.renderEngine;

import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.window.WindowMaster;

public class Launcher {
  private static  ShaderMaster shaderMaster;
  public static void main(String[] args) {
    // Window Init
    WindowMaster window = new WindowMaster();
    window.init();

    // Shader Init
    shaderMaster = new ShaderMaster("shaders/vertex.glsl", "shaders/fragment.glsl");
    House house = new House();

    ItemManger itemManger = new ItemManger();
    itemManger.addHouse(house);


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
}