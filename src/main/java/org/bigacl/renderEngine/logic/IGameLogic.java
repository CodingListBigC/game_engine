package org.bigacl.renderEngine.logic;

public interface IGameLogic {
  void input();           // Handle input
  void update(float delta); // Update game state (delta = time since last frame)
  void render();          // Render the scene
  void cleanup();         // Clean up resources
}