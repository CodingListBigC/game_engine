package org.bigacl.renderEngine.gameManger.logic;

import org.bigacl.renderEngine.player.Player;
import org.joml.Vector3f;

public interface IGameLogic {
  void input();           // Handle input
  void update(float delta); // Update game state (delta = time since last frame)
  void render();          // Render the scene

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
  void renderHud();

  void cleanup();         // Clean up resources

  default Player getPlayer(){
    return new Player("", "", new Vector3f(0.0f,0.0f,0.0f));
  };
}