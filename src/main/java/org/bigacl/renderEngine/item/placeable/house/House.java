package org.bigacl.renderEngine.item.placeable.house;

import com.google.gson.Gson;
import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.texture.Texture;
import org.joml.Vector3f;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class House extends BasePlaceableItem {
  private final String folderPath = "gameData/buildings/home";
  private final String jsonName = "home.json";

  public House() {
    // Set isPlaced true for testing so it triggers loadModel() in render()
    this.isPlaced = true;
    loadData();
  }

  private void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) {
        System.err.println("Could not find file: " + jsonFilePath);
        return;
      }

      Gson gson = new Gson();
      Reader reader = new InputStreamReader(in);
      HouseData data = gson.fromJson(reader, HouseData.class);

      if (data != null) {
        this.name = data.name;
        this.baseModel = data.baseModel;
        this.level = data.level;
        this.amount_of_levels = data.amount_of_levels;
        this.type = data.type;
      }
    } catch (Exception e) {
      System.err.println("Error loading House JSON: " + e.getMessage());
      e.printStackTrace();
    }
  }
  @Override
  protected void loadModel() {
    String levelKey = String.valueOf(this.currentLevel);
    LevelData levelData = this.level.get(levelKey);

    if (levelData == null || levelData.make == null) return;

    currentMeshes.clear();

    for (MakeData placement : levelData.make.values()) {
      try {
        // 1. Get the Part Config (using ID "0" or "1")
        BaseModelParts partConfig = this.baseModel.get(placement.item.id);
        if (partConfig == null) continue;

        // 2. Get the specific Link ("DF")
        SeparatePartsLink link = partConfig.separateParts.get(placement.item.type);
        if (link == null) continue;

        // 3. Load Mesh and Texture
        Mesh mesh = OBJLoader.loadOBJ(folderPath + "/" + link.model);
        mesh.setTexture(new Texture(folderPath + "/" + link.texture));

        // 4. Handle missing 'pos' block safely
        // If JSON part has no "pos", placement.pos will be null.
        // We use our default (0,0,0) if it's missing.
        PositionData p = (placement.pos != null) ? placement.pos : new PositionData();

        // 5. BLENDER TO JAVA COORDINATE SWAP
        // Blender X -> Java X
        // Blender Z -> Java Y (Height)
        // Blender Y -> Java -Z (Depth)
        mesh.setPosition(
                p.x,
                p.z,   // Note: We use JSON 'z' for Java 'y'
                -p.y   // Note: We use JSON 'y' for Java '-z'
        );

        currentMeshes.add(mesh);
      } catch (Exception e) {
        System.err.println("Failed to load part: " + e.getMessage());
      }
    }
  }

}