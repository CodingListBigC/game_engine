package org.bigacl.renderEngine;

import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
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


    Mesh cube = OBJLoader.loadOBJ("model/cube.obj");

    IGameLogic gameLogic = new GameLogic(window, shaderMaster, cube);
    // Render loop
    window.loop(gameLogic);

    // Cleanup
    cube.cleanup();
    shaderMaster.cleanup();
    window.cleanup();
  }


  public static ShaderMaster getShaderMaster() {
    return shaderMaster;
  }
}