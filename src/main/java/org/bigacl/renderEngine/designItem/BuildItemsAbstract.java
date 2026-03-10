package org.bigacl.renderEngine.designItem;

import com.google.gson.Gson;
import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.model.mesh.OBJLoader;
import org.bigacl.renderEngine.model.texture.Texture;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public abstract class BuildItemsAbstract extends BasePlaceableItem {
  protected String jsonName = "default.json";
  protected String folderPath;

  protected int numberOfTypes;
  protected String name;
  protected int currentType = 0;

  // Holds the raw parsed data from JSON
  protected BuildItemData rawData;

  public void init() {
    defaultSettings();
    loadData();
    loadModel();
  }

  @Override
  protected void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;

    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) {
        System.err.println("File not found: " + jsonFilePath);
        return;
      }

      Gson gson = new Gson();
      try (InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
        this.rawData = gson.fromJson(reader, BuildItemData.class);

        if (rawData != null) {
          // Pull top-level data into class variables
          this.name = (rawData.name != null) ? rawData.name.main : "Unknown";
          this.numberOfTypes = rawData.number_of_types;
          System.out.println("Successfully loaded Item: " + this.name);
        }
      }
    } catch (Exception e) {
      System.err.println("Error parsing JSON for " + jsonName);
      e.printStackTrace();
    }
  }

  protected void loadModel() {
    if (rawData == null || rawData.modelTypes == null) return;

    // Clear existing meshes before loading new type
    currentMeshes.clear();

    // Safety check for range
    String typeKey = String.valueOf(Math.min(currentType, numberOfTypes));
    BuildItemData.ModelTypeDetails typeDetails = rawData.modelTypes.get(typeKey);

    if (typeDetails == null) {
      System.err.println("Type data missing for index: " + typeKey);
      return;
    }

    try {
      String partFileName = typeDetails.separateParts.model;
      String textureFile = typeDetails.separateParts.texture.values().stream().findFirst().orElse(null);

      if (partFileName != null) {
        Mesh mesh = OBJLoader.loadOBJ(folderPath + "/" + partFileName);

        if (textureFile != null && !textureFile.isEmpty()) {
          mesh.setTexture(new Texture(folderPath + "/" + textureFile));
        }

        // Standard positioning logic
        BasePlaceableItem.XYZMatrix p = new BasePlaceableItem.XYZMatrix();
        mesh.setPosition(p.x, p.z, -p.y);

        currentMeshes.add(mesh);
      }
    } catch (Exception e) {
      System.err.println("Failed to load model parts: " + e.getMessage());
    }

    updateModelMatrix();
  }

  public void setType(int nextType) {
    if (nextType < 0 || nextType > numberOfTypes) {
      throw new IndexOutOfBoundsException("Type " + nextType + " out of range for " + name);
    }
    this.currentType = nextType;
    loadModel(); // Reload meshes for the new type
  }

  public void setNextType() {
    this.currentType = (this.currentType + 1) % (numberOfTypes + 1);
    loadModel();
  }

  public ArrayList<String> getTypesNameList() {
    ArrayList<String> names = new ArrayList<>();
    if (rawData != null && rawData.modelTypes != null) {
      for (BuildItemData.ModelTypeDetails details : rawData.modelTypes.values()) {
        names.add(details.partName);
      }
    }
    return names;
  }
}