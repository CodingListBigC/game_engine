package org.bigacl.renderEngine.gameManger;

import org.bigacl.renderEngine.player.Player;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.bigacl.renderEngine.utils.consts.ClassConst.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public interface MainLogic {
  void input();           // Handle input
  void update(float delta); // Update game state (delta = time since last frame)

  default void render() {
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
    nanoVGUI.beginFrame(windowWidth, windowHeight, 1.0f);
    renderHud();
    nanoVGUI.endFrame();
    glEnable(GL_DEPTH_TEST);
  }

  /**
   * Renders all 3D entities and world geometry.
   * <p>
   * This method handles the perspective rendering pass, including models,
   * terrain, and lighting calculations.
   */
  void render3dModels();

  /**
   * Renders the Heads-Up Display (HUD) and 2D UI elements.
   * <p>
   * This method is called after the 3D pass to ensure UI elements
   * are drawn on top of the world geometry.
   */
  default void renderHud() {
    if (ClassConst.hudAbstract == null) {
      initializeHud();
    }

    // Ensure we don't call render if initialization failed
    if (ClassConst.hudAbstract != null) {
      ClassConst.hudAbstract.render();
    }
  }

  default void initializeHud() {
    ClassConst.setHudAbstract();
  }

  default void cleanup() {

  }



  default Player getPlayer(){
    return new Player("", "", new Vector3f(0.0f,0.0f,0.0f));
  }
}