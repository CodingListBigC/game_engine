package org.bigacl.renderEngine.item.placeable;

import org.bigacl.renderEngine.item.ItemInterface;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.texture.Texture;
import org.bigacl.renderEngine.mesh.OBJLoader; // Assuming you have an OBJ loader
import org.joml.Vector3f;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePlaceableItem implements ItemInterface, PlaceableInterface {

  protected NameData name;
  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  protected int amount_of_levels;
  protected String type;
  protected Map<String, LevelData> level;
  protected int currentLevel = 1;
  protected List<Mesh> currentMeshes = new ArrayList<>();
  protected boolean isPlaced = false;

  protected abstract void loadModel();

  protected boolean checkPlace() {
    return true;
  }

  // --- Data Structures for GSON ---
  public static class NameData {
    public String main;
    public String plural;
  }


  public static class BaseModelParts {
    public String partName;
    public int defaultFacing;
    public Map<String, SeparatePartsLink> separateParts;
  }

  public static class SeparatePartsLink {
    public String name;
    public String model;
    public String texture;
  }

  public static class LevelData {
    public int price;
    public int requiredLevel;
    public Map<String, MakeData> make; // Maps to the "make" key in JSON
  }

  public static class MakeData {
    public ItemPointer item;
    public PositionData pos;
  }

  public static class ItemPointer {
    public String id; // The key in baseModel
    public String type; // The key in separateParts (e.g., "DF")
  }

  public static class PositionData {
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;
  }

  // --- Interface Methods ---
  @Override
  public void place(Vector3f position, float rotation) {
    this.isPlaced = true;
    loadModel();
    // Here you would apply 'position' and 'rotation' to all meshes
  }

  @Override
  public boolean checkPlace(Vector3f position, float rotation) {
    return true;
  }

  @Override public void init() {}
  @Override public void leftClick() {}
  @Override public void rightClick() {}

  @Override
  public void render() {
    if (isPlaced) {
      if (currentMeshes.isEmpty()) {
        loadModel();
      }
      for (Mesh mesh : currentMeshes) {
        mesh.render();
      }
    }
  }

  @Override public void cleanup() {
    for (Mesh mesh : currentMeshes) {
      mesh.cleanup();
    }
  }
}