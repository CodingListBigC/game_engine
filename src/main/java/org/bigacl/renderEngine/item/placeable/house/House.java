package org.bigacl.renderEngine.item.placeable.house;

import com.google.gson.Gson;
import org.bigacl.renderEngine.MainGame;
import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.texture.Texture;
import org.bigacl.renderEngine.utils.consts.ClassConst;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class House extends BasePlaceableItem {
  private final String folderPath = "gameData/buildings/home";
  private final String jsonName = "home.json";

  public House() {
    this.isPlaced = true;
    loadData();
  }
  private void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) return;

      Gson gson = new Gson();
      Reader reader = new InputStreamReader(in);
      HouseData data = gson.fromJson(reader, HouseData.class);

      if (data != null) {
        this.name = data.name;
        this.level = data.level;
        this.amount_of_levels = data.amount_of_levels;
        this.type = data.type;
      }
    } catch (Exception e) {
      System.err.println("Error loading House: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public boolean checkPlace(){
    return true;
  }
  @Override
  protected void loadModel() {
    String levelKey = String.valueOf(this.currentLevel);
    LevelData levelData = this.level.get(levelKey);

    if (levelData == null || levelData.models == null) {
      System.err.println("No data found for level: " + levelKey);
      return;
    }

    // Clear existing meshes if re-loading
    currentMeshes.clear();

    // Loop through "0", "1", etc. in the models section
    for (ModelPartData part : levelData.models.values()) {
      String fullModelPath = folderPath + "/" + part.model;
      String fullTexturePath = folderPath + "/" + part.texture;

      try {
        Mesh mesh = OBJLoader.loadOBJ(fullModelPath);
        Texture tex = new Texture(fullTexturePath);
        mesh.setTexture(tex);

        currentMeshes.add(mesh);
      } catch (Exception e) {
        System.err.println("Failed to load model part: " + part.model);
      }
    }
  }
}