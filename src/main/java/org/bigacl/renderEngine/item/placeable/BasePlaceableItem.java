package org.bigacl.renderEngine.item.placeable;

import org.bigacl.renderEngine.item.ItemInterface;
import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.texture.Texture;
import org.joml.Vector3f;
import java.util.Map;

public abstract class BasePlaceableItem implements ItemInterface, PlaceableInterface {

  // These fields will be filled by Gson in the child classes

  protected NameData name;
  protected int amount_of_levels;
  protected String type;
  protected Map<String, LevelData> level;
  protected int currentLevel = 1;
  protected java.util.List<Mesh> currentMeshes = new java.util.ArrayList<>();
  protected boolean isPlaced;
  protected Texture currentMeshTexture;

  protected abstract void loadModel();
  protected boolean checkPlace(){
    return true;
  }

  // Move the data classes here so they are accessible to all children
  public static class NameData {
    public String main;
    public String plural;
  }

  public static class LevelData {
    public int price;
    public Map<String, ModelPartData> models;
    public StatsData stats;
    public int requiredLevel;
  }
  public static class ModelPartData {
    public String name;
    public String model;
    public String texture;
  }

  public static class StatsData {
    public int people_can_hold;
  }


  // --- Interface Methods ---

  @Override
  public void place(Vector3f position, float rotation) {
  }

  @Override
  public boolean checkPlace(Vector3f position, float rotation) {
    // Logic to check if the area is clear (return true if okay to build)
    return true;
  }

  @Override public void init() {}
  @Override public void leftClick() {}
  @Override public void rightClick() {}
  @Override public void render() {
    if (checkPlace())
    if (isPlaced) {
      if (currentMeshes.isEmpty()){
        loadModel();
      }
      for (Mesh mesh : currentMeshes) {
        mesh.render();
      }
    }
  }
  @Override public void cleanup() {}
}