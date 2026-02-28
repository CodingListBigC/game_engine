package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build.BuildMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;

public class MasterHud extends ModelDesignAbstractClass {

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    this.leftMenu.checkHudInputs(mouseLocation, mouseAction);
    this.rightMenu.checkHudInputs(mouseLocation, mouseAction);
    this.buildMaster.checkHudInputs(mouseLocation, mouseAction);
  }

  HudAbstract leftMenu = new LeftHud();
  HudAbstract rightMenu = new RightHud();
  BuildMaster buildMaster = new BuildMaster();

  public MasterHud() {
    debugMenu = new DebugMenu();

  }


  @Override
  public void renderMenu() {
    this.leftMenu.renderMenu();
    this.rightMenu.renderMenu();
    this.buildMaster.renderMenu();
  }

  @Override
  public void updateText(){

  }


}
