package org.bigacl.test.launcher;

import org.bigacl.renderEngine.utils.consts.ClassConst;

import static org.bigacl.renderEngine.utils.consts.ClassConst.*;

public class ModelDesignLauncher {
  public static void main(String[] args) {
    ClassConst.setHudAbstract(1);

    // Start Main Loop
    window.loop();

    // Clean up functions
    itemManger.cleanupAll();
    shader3d.cleanup();
    window.cleanup();
  }
}
