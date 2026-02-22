package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;

public class MasterHud extends ModelDesignAbstractClass {

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


  public void checkHudInputs() {
    this.leftMenu.checkHudInputs();
  }

  @Override
  public void updateText(){

  }
}
