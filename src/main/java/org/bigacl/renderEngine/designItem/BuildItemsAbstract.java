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

public abstract class BuildItemsAbstract extends ModelAbstract {
  // Item Location Variable
  protected String jsonName = "default.json";
  protected String folderPath;

  // Item Data
  protected int numberOfTypes;
  protected String name;
  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  protected Map<String, BuildItemsAbstract.ItemTypeData> modelType;

  public static class ItemTypeData {
    public int type;
    public Map<String, ItemModelTypes> make;
  }

  public static class ItemModelTypes {
    public String partName;
    public int defaultFacing;
    public SeparatePartsLink separatePartsLink;
  }

  public static class SeparatePartsLink {
    public String model;
    public Map<String, String> textureClass;
  }

  protected abstract void initData();

  /**
   * Init BuildItemAbstract
   * By settings all the correct data variables and making the models.
   */
  protected void init() {
    initData();
    loadData();
    loadModel();
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
        this.numberOfTypes = data.number_of_types;
        this.modelType = data.modelsTypes;
      }
    } catch (Exception ignored) {
    }
  }

  protected void loadModel() {
    // Make sure current type is in range
    this.currentType = Math.min(currentType, numberOfTypes);

    ItemTypeData itemData = modelType.get(String.valueOf(this.currentType));

    for (ItemModelTypes placement : itemData.make.values()) {
      try {
        String partFileName = placement.separatePartsLink.model;
        String texture = placement.separatePartsLink.textureClass.get("0");

        if (partFileName == null || texture == null) return;

        Mesh mesh = OBJLoader.loadOBJ(folderPath + "/" + partFileName);
        mesh.setTexture(new Texture(folderPath + "/" + texture));

        BasePlaceableItem.XYZMatrix p = new BasePlaceableItem.XYZMatrix();

        mesh.setPosition(p.x, p.z, -p.y);

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
    ArrayList<String> returnArray = new ArrayList<>();
    // TODO: Able to add item to list then return list of items
    return returnArray;
  }

}
