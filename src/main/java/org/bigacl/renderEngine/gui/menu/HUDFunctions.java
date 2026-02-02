package org.bigacl.renderEngine.gui.menu;

import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class HUDFunctions {
  public static Vector2f hudItemPos(Vector2f startPos, int postion, int size){
    return new Vector2f(startPos.x, (startPos.y + (postion * (size + Const.PADDING))));
  }
}
