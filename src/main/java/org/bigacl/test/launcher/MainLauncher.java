package org.bigacl.test.launcher;

import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.camera.Camera;
import org.bigacl.renderEngine.gui.font.NanoVGUI;
import org.bigacl.renderEngine.item.ItemManger;
import org.bigacl.renderEngine.item.placeable.aparment.Aparment;
import org.bigacl.renderEngine.item.placeable.house.House;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.player.level.MainLevel;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.window.WindowMaster;
import org.joml.Vector3f;

public class MainLauncher {
  public static void main(String[] args) {
    // Window Init
    WindowMaster window = ClassConst.window;

    ShaderMaster shaderMaster = ClassConst.shader3d;
    House house = new House();

    // Item Manger Iit
    ItemManger itemManger = ClassConst.itemManger;
    itemManger.addItem(house);
    Aparment aparment = new Aparment();
    aparment.place(new Vector3f(10,0,10), 0);
    itemManger.addItem(aparment);
    NanoVGUI nanoVGUI = new NanoVGUI();
    NanoVGUI NanoVGUI = new NanoVGUI();
    // Render loop
    window.loop();

    // Clean up functions
    itemManger.cleanup();
    shaderMaster.cleanup();
    window.cleanup();
  }
}