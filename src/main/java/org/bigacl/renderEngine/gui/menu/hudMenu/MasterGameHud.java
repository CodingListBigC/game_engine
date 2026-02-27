package org.bigacl.renderEngine.gui.menu.hudMenu;

import org.bigacl.renderEngine.gui.menu.MainGameHud;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.joml.Vector2d;

public class MasterGameHud extends HudAbstract {

  @Override
  public void updateText() {

  }

  @Override
  public void checkHudInputs(Vector2d mouseLocation) {

  }

  @Override
  public void renderMenu() {

  }


  private static MainGameHud mainGameHud = null;

  public MasterGameHud() {
    debugMenu = new DebugMenu();
    mainGameHud = new MainGameHud();
  }
}
