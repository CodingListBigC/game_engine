package org.bigacl.renderEngine.item.placeable.house;

import com.google.gson.Gson;
import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.texture.Texture;

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
  protected void loadModel(){
    String levelNumberString = String.valueOf(this.currentLevel);
    String modelFileName = this.level.get(levelNumberString).model;
    // IMPORTANT: Make sure this path matches where your .obj actually is!
    String fullModelPath = folderPath +"/" + modelFileName;
    // Load it into the variable defined in BasePlaceableItem
    this.currentMesh = OBJLoader.loadOBJ(fullModelPath);
    String textureFileName= this.level.get(levelNumberString).texture;
    String fullTexturePath = folderPath +"/" +textureFileName;
    this.currentMeshTexture = new Texture(fullTexturePath);
    this.currentMesh.setTexture(this.currentMeshTexture);
  }
}