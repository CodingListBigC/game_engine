package org.bigacl.test.launcher;

import org.bigacl.renderEngine.gameItems.item.ItemMangerMainGame;
import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Apartment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
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
    ItemMangerMainGame itemManger = ClassConst.itemManger;

    // Add House item
    House house = new House();
    itemManger.addItemAll(house);

    // Add Apartment Item
    Apartment apartment = new Apartment();
    apartment.place(new Vector3f(10, 0, 10), 0);
    itemManger.addItemAll(apartment);

    // Render loop
    window.loop();

    // Clean up functions
    itemManger.cleanupAll();
    shaderMaster.cleanup();
    window.cleanup();
  }
}