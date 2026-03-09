package org.bigacl.renderEngine.designItem;

import com.google.gson.Gson;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.model.ModelAbstract;
import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.model.mesh.OBJLoader;
import org.bigacl.renderEngine.model.texture.Texture;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class BuildItemsAbstract extends ModelAbstract {
  protected String jsonName;
  protected String folderPath;

  // Item Data
  protected int numberOfTypes;
  protected String name;

  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  protected Map<String, ItemTypeData> modelsPath;

  public static class ItemTypeData {
    public int type;
    public Map<String, BasePlaceableItem.MakeData> make;
  }


  // Item Type
  protected int currentType;

  @Override
  protected void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) return;

      Gson gson = new Gson();
      InputStreamReader reader = new InputStreamReader(in);

      // 1. Load the raw data from JSON
      BuildItemData data = gson.fromJson(reader, BuildItemData.class);

      if (data != null) {
        this.name = data.name;
        this.numberOfTypes = data.numberOfTypes;
        this.modelsPath = data.modelsPath;
      }
    } catch (Exception ignored) {
    }
  }

  protected void loadModel() {
    // Make sure current type is in range
    this.currentType = Math.min(currentType, numberOfTypes);

    ItemTypeData itemData = modelsPath.get(String.valueOf(this.currentType));

    if (itemData == null || itemData.make == null) return;


    for (BasePlaceableItem.MakeData placement : itemData.make.values()) {
      try {
        BasePlaceableItem.BaseModelParts partConfig = this.baseModel.get(placement.item.id);
        if (partConfig == null) continue;

        BasePlaceableItem.SeparatePartsLink link = partConfig.separateParts.get(placement.item.type);
        if (link == null) continue;

        Mesh mesh = OBJLoader.loadOBJ(folderPath + "/" + link.model);
        mesh.setTexture(new Texture(folderPath + "/" + link.texture));

        BasePlaceableItem.XYZMatrix p = (placement.pos != null) ? placement.pos : new BasePlaceableItem.XYZMatrix();
        BasePlaceableItem.XYZMatrix origin = (link.origin != null) ? link.origin : new BasePlaceableItem.XYZMatrix();

        mesh.setPosition(
                p.x - origin.x,
                p.z - origin.z,
                -p.y + origin.y
        );

        currentMeshes.add(mesh);
      } catch (Exception e) {
        System.err.println("Failed to load part: " + e.getMessage());
      }
    }
    updateModelMatrix();
  }

  /**
   * Set current type for item.
   *
   * @param nextType Next type for current item
   * @throws IndexOutOfBoundsException If nextType is out of range
   */
  public void setType(int nextType) throws IndexOutOfBoundsException {
    if (0 <= nextType && nextType <= numberOfTypes) {
      throw new IndexOutOfBoundsException();
    }
    this.currentType = nextType;
  }

  public void setNextType() {
    this.currentType++;
    if (this.currentType > numberOfTypes) {
      this.currentType -= numberOfTypes;
    }
  }

  public ArrayList<String> getTypesNameList() {
    ArrayList<String> returnArray = new ArrayList<String>();
    // TODO: Able to add item to list then return list of items
    return returnArray;
  }

}
