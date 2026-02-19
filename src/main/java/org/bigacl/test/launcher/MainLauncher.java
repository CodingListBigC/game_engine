package org.bigacl.test.launcher;

import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.aparment.Aparment;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector3f;

public class MainLauncher {
  public static void main(String[] args) {
    // Setup Hud Mode
    ClassConst.setHudAbstract();
    // Get class from consts
    WindowMaster window = ClassConst.window;
    ShaderMaster shaderMaster = ClassConst.shader3d;
    ItemManger itemManger = ClassConst.itemManger;

    // Add House item
    House house = new House();
    itemManger.addItem(house);

    // Add Aparment Item
    Aparment aparment = new Aparment();
    aparment.place(new Vector3f(10,0,10), 0);
    itemManger.addItem(aparment);

    // Render loop
    window.loop();

    // Clean up functions
    itemManger.cleanup();
    shaderMaster.cleanup();
    window.cleanup();
  }
}