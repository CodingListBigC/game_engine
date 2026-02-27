package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;

public class MasterHud extends ModelDesignAbstractClass {

  @Override
  public void checkHudInputs(Vector2d mouseLocation) {
    this.leftMenu.checkHudInputs(mouseLocation);
    this.rightMenu.checkHudInputs(mouseLocation);
  }

  HudAbstract leftMenu;
  HudAbstract rightMenu;

  public MasterHud() {
    debugMenu = new DebugMenu();
    this.leftMenu = new LeftHud();
    this.rightMenu = new RightHud();

  }


  @Override
  public void renderMenu() {
    this.leftMenu.renderMenu();
    this.rightMenu.renderMenu();
  }

  @Override
  public void updateText(){

  }
}
