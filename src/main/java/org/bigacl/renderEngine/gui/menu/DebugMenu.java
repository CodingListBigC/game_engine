package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2f;


public class DebugMenu {


  // Import Other Data
  private final static IGameLogic game = ClassConst.game;
  private final static NanoVGUI nanoVGUI = ClassConst.nanoVGUI;
  // Debug Menu Vairables
  private boolean status = false;
  public DebugMenu() {
  }

  public void renderMenu(){
  }

  private void cameraDebug(Vector2f location){
    // Camera Menu
    Vector2f boxSize = null;
  }

  // Debug Status Functions
  public void toogleStats(){
    status = !status;
  }
  public boolean getStatus() {
    return status;
  }
  public void setStatus(boolean status) {
    this.status = status;
  }
}
