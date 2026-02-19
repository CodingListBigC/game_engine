package org.bigacl.test.launcher;

import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;

public class ModelDesignLauncher {
  public static void main(String[] args) {
    ClassConst.setHudAbstract();
    // Get class from consts
    WindowMaster window = ClassConst.window;
    ShaderMaster shaderMaster = ClassConst.shader3d;
    ItemManger itemManger = ClassConst.itemManger;

    // Start Main Loop
    window.loop();

    // Clean up functions
    itemManger.cleanup();
    shaderMaster.cleanup();
    window.cleanup();
  }
}
