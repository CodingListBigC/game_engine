package org.bigacl.test.launcher;

import org.bigacl.renderEngine.gameItems.item.placeable.aparment.Apartment;
import org.bigacl.renderEngine.gameItems.item.placeable.house.House;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.item.ItemUtils;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector3f;

import static org.bigacl.renderEngine.utils.consts.ClassConst.window;

public class MainLauncher {
  public static void main(String[] args) {
    // Setup Hud Mode
    ClassConst.setHudAbstract();

    // Add House item
    House house = new House();
    ItemUtils.addItemHelper(house);

    // Add Apartment Item
    Apartment apartment = new Apartment();
    apartment.place(new Vector3f(10, 0, 10), 0);
    ItemUtils.addItemHelper(apartment);

    // Render loop
    window.loop();

    ClassConst.cleanup();
  }
}