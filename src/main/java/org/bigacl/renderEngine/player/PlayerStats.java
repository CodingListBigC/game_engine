package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.player.level.Level;
import org.joml.Vector3f;

/**
 * @param username PlayerStats class is a separate way to store player stats.
 *                 This class is not allowed at any time to edit original Player stats;
 *                 you can update it but do not copy the reference of the data if it's mutable.
 */
public record PlayerStats(String username, Vector3f mainColor, int money, Level level) {

  /**
   * Creates a read-only snapshot of player data for the GUI.
   *
   * @param username  The display name of the player.
   * @param mainColor The primary theme color (defensively copied to prevent modification).
   * @param money     The current amount of currency the player holds.
   * @param level     The player's current level object containing experience data.
   */
  public PlayerStats(String username, Vector3f mainColor, int money, Level level) {
    this.username = username;
    // Defensive copy: creates a new vector so the UI cannot change the player's actual color
    this.mainColor = new Vector3f(mainColor);
    this.money = money;
    this.level = level;
  }

  @Override
  public Level level() {
    if (level == null) {
      return new Level();
    }
    return level;
  }
}