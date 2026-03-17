package org.bigacl.renderEngine.designItem.saveModel;

import com.google.gson.Gson;
import org.bigacl.renderEngine.designItem.build.BuildItemData;
import org.bigacl.renderEngine.designItem.build.BuildItemsAbstract;
import org.bigacl.renderEngine.utils.consts.ClassConst;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SaveModelJson {
  public static void saveItemManger() {
    SaveModelData data = new SaveModelData();

    ArrayList<BuildItemsAbstract> defaultData = (ArrayList<BuildItemsAbstract>) ClassConst.itemMangerAbstract.getDefaultData();

    data.name = new BuildItemData.NameInfo();

    for (BuildItemsAbstract item : defaultData) {
      Class<?> itemClass = item.getClass();
      String currentType = String.valueOf(item.getCurrentType());
      SaveModelData.ModelItem newModel = new SaveModelData.ModelItem();
      newModel.classType = String.valueOf(itemClass);
      newModel.itemType = currentType;
      newModel.position = new SaveModelData.InfoVector3f(item.getPosition());
      newModel.rotation = new SaveModelData.InfoVector3f(item.getRotation());

      int location = data.models.isEmpty() ? 0 : data.models.size() - 1;

      data.models.put(String.valueOf(location), newModel);
    }

    saveData(data);
  }

  public static void saveData(SaveModelData data) {
    Gson gson = new Gson();

    // To JSON String
    String jsonString = gson.toJson(data);

    // To JSON File
    try (FileWriter writer = new FileWriter("testData.json")) {
      gson.toJson(data, writer);
    } catch (IOException e) {
    }
  }
}
