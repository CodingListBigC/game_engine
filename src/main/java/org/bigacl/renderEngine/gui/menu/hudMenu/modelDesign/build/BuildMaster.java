package org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.build;

import org.bigacl.renderEngine.gui.fields.button.Button;
import org.bigacl.renderEngine.gui.menu.hudMenu.modelDesign.ModelDesignAbstractClass;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.bigacl.renderEngine.utils.ColorSetter.setNewAlpha;

public class BuildMaster extends ModelDesignAbstractClass {
  boolean menuStatus = false;
  Button openCloseBtn;

  public BuildMaster() {
    makeOpenCloseButton();
  }

  @Override
  public void checkHudInputs(Vector2d mouseLocation, int mouseAction) {
    if (mouseAction == GLFW.GLFW_MOUSE_BUTTON_RIGHT){
      leftClickInput(mouseLocation);
    }
  }

  private void leftClickInput(Vector2d mouseLocation){
    if (openCloseBtn.isHovered(mouseLocation)) {
      menuStatus = !menuStatus;
    }

  }

  @Override
  public void updateText() {

  }

  @Override
  public void renderMenu() {
    openCloseBtn.render();
  }

  public void makeOpenCloseButton(){
    Vector2f buttonScreenPercentages = new Vector2f(0.125f, 0.10f);
    Vector2f buttonSize= new Vector2f(ClassConst.window.getWidth() * buttonScreenPercentages.x, buttonScreenPercentages.y * ClassConst.window.getHeight());
    Vector2f buttonLocation  = new Vector2f(0, ClassConst.window.getHeight()).sub(0f,buttonSize.y);
    this.openCloseBtn = new Button("Open Build", "",buttonSize, buttonLocation, setNewAlpha(Color.DARK_GRAY, 100),Color.LIGHT_GRAY);

  }
}
