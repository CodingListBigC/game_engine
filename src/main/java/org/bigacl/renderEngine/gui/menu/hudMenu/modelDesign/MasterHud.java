package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign;

import org.bigacl.renderEngine.designItem.saveModel.SaveModelJson;
import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.menu.hudMenu.HudAbstract;
import org.bigacl.renderEngine.gui.menu.debugMenu.DebugMenu;
import org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build.BuildMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class MasterHud extends ModelDesignAbstractClass {

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    this.leftMenu.checkHudInputs(mouseLocation, mouseAction);
    this.rightMenu.checkHudInputs(mouseLocation, mouseAction);
    this.buildMaster.checkHudInputs(mouseLocation, mouseAction);
    if (saveButton.checkInput(mouseLocation, mouseAction, GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      SaveModelJson.saveItemManger();
    }
  }

  HudAbstract leftMenu = new LeftHud();
  HudAbstract rightMenu = new RightHud();
  BuildMaster buildMaster = new BuildMaster();
  Button saveButton = new Button("Save Model", Color.BLUE, Color.WHITE);

  public MasterHud() {
    debugMenu = new DebugMenu();
    saveButton.setLocation(new Vector2f(500, 0));
    saveButton.setSize(Const.DEFAULT_BUTTON_SIZE);
    saveButton.setVisible(true);

  }


  @Override
  public void renderMenu() {
    this.leftMenu.renderMenu();
    this.rightMenu.renderMenu();
    this.buildMaster.renderMenu();
    this.saveButton.render();
  }

  @Override
  public void updateText(){

  }


}
