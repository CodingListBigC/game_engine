package org.bigacl.renderEngine.item.placeable;

import org.bigacl.renderEngine.item.ItemInterface;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.player.BoundingBox;
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
  protected BoundingBox boundingBox;
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
    public XYZMatrix size;
    public XYZMatrix origin;
  }

  public static class LevelData {
    public int price;
    public int requiredLevel;
    public Map<String, MakeData> make; // Maps to the "make" key in JSON
  }

  public static class MakeData {
    public ItemPointer item;
    public XYZMatrix pos;
  }

  public static class ItemPointer {
    public String id; // The key in baseModel
    public String type; // The key in separateParts (e.g., "DF")
  }

  public static class XYZMatrix {
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;
  }

  // --- Interface Methods ---
  @Override
  public void place(Vector3f position, float rotation) {
    this.isPlaced = true;
    loadModel();
    // 1. Calculate the total Bounding Box based on the main part size
    // We look for the first part in the current level to set the hitbox
    LevelData currentData = level.get(String.valueOf(currentLevel));
    if (currentData != null) {
      for (MakeData make : currentData.make.values()) {
        BaseModelParts part = baseModel.get(make.item.id);
        SeparatePartsLink link = part.separateParts.get(make.item.type);

        if (link.size != null) {
          this.boundingBox = new BoundingBox(position, link.size);
          System.out.println("Hitbox created for " + name.main + " at " + position);
          break; // Use the first defined size as the primary hitbox
        }
      }
    }
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
  public BoundingBox getBoundingBox() {
    return boundingBox;
  }
  public void setupHitbox() {
    if (this.currentMeshes.isEmpty()) loadModel();

    LevelData currentData = level.get(String.valueOf(currentLevel));
    if (currentData == null || currentData.make == null) return;

    // Reset so we don't grow infinitely if called twice
    this.boundingBox = null;

    for (MakeData make : currentData.make.values()) {
      if (make.pos == null) make.pos = new XYZMatrix();

      BaseModelParts part = baseModel.get(String.valueOf(make.item.id));
      if (part == null) continue;

      SeparatePartsLink link = part.separateParts.get(make.item.type);
      if (link != null && link.size != null) {
        XYZMatrix origin = (link.origin != null) ? link.origin : new XYZMatrix();

        // Blender -> Java Coordinate Swap + Origin Offset
        Vector3f partPos = new Vector3f(
                make.pos.x - origin.x,
                make.pos.z - origin.z, // Blender Z is Java Y (Up)
                -make.pos.y + origin.y // Blender Y is Java -Z (Depth)
        );

        // Create a temporary box for this specific part
        BoundingBox partBox = new BoundingBox(partPos, link.size);

        // Merge it into the main boundingBox
        if (this.boundingBox == null) {
          this.boundingBox = partBox;
        } else {
          this.boundingBox.merge(partBox);
        }
      }
    }
  }
}