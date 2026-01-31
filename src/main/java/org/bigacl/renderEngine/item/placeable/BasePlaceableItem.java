package org.bigacl.renderEngine.item.placeable;

import org.bigacl.renderEngine.item.ItemInterface;
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
  protected Mesh currentMesh;
  protected boolean isPlaced;
  protected Texture currentMeshTexture;

  protected abstract void loadModel();


  // Move the data classes here so they are accessible to all children
  public static class NameData {
    public String main;
    public String plura;
  }

  public static class LevelData {
    public int price;
    public String model;
    public String texture;
    public StatsData stats;
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
    if (isPlaced) {
      if (currentMesh == null){
        loadModel();
      }
      if (currentMesh != null) {
        currentMesh.render();
      }else{
        System.out.println("Model is not getting loaded, name: " + name.main + ", type: " + type );
      }
    }
  }
  @Override public void cleanup() {}
}