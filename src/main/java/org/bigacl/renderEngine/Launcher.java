package org.bigacl.renderEngine;

import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.texture.Texture;
import org.bigacl.renderEngine.window.WindowMaster;

public class Launcher {
  private static  ShaderMaster shaderMaster;
  public static void main(String[] args) {
    // Window Init
    WindowMaster window = new WindowMaster();
    window.init();

    // Shader Init
    shaderMaster = new ShaderMaster("shaders/vertex.glsl", "shaders/fragment.glsl");


    Mesh house = OBJLoader.loadOBJ("models/house_small-1.obj");
    Texture houseTexture = new Texture("house_small-1.png");
    house.setTexture(houseTexture);


    IGameLogic gameLogic = new GameLogic(window, shaderMaster, house);
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