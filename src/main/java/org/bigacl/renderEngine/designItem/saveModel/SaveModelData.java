package org.bigacl.renderEngine.designItem.saveModel;

import org.bigacl.renderEngine.designItem.build.BuildItemData;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class SaveModelData {
  public BuildItemData.NameInfo name;

  public Map<String, ModelItem> models = new HashMap<>();

  public static class ModelItem {
    String classType;
    String itemType;
    InfoVector3f position;

    public String getClassType() {
      return classType;
    }

    public String getItemType() {
      return itemType;
    }

    public InfoVector3f getPosition() {
      return position;
    }

    public InfoVector3f getRotation() {
      return rotation;
    }

    InfoVector3f rotation;
  }

  public static class InfoVector3f {
    public InfoVector3f(Vector3f info) {
      this.x = info.x;
      this.y = info.y;
      this.z = info.z;
    }

    float x;
    float y;
    float z;
  }

}
