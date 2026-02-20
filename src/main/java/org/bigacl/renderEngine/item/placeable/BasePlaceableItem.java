package org.bigacl.renderEngine.item.placeable;

import com.google.gson.Gson;
import org.bigacl.renderEngine.item.ItemInterface;
import org.bigacl.renderEngine.mesh.Mesh;
import org.bigacl.renderEngine.mesh.OBJLoader;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.texture.Texture;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePlaceableItem implements ItemInterface, PlaceableInterface {

  // File management
  protected String folderPath;
  protected String jsonName;
  protected NameData name;
  public Map<String, BasePlaceableItem.BaseModelParts> baseModel;
  protected int amount_of_levels;
  protected String type;
  protected Map<String, LevelData> level;
  protected int currentLevel = 1;
  protected List<Mesh> currentMeshes = new ArrayList<>();
  protected boolean isPlaced = false;
  protected BoundingBox boundingBox;

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
        XYZMatrix p = (placement.pos != null) ? placement.pos : new XYZMatrix();

        XYZMatrix origin = (link.origin != null) ? link.origin : new XYZMatrix();
        // 5. BLENDER TO JAVA COORDINATE SWAP
        // Blender X -> Java X
        // Blender Z -> Java Y (Height)
        // Blender Y -> Java -Z (Depth)
        mesh.setPosition(
                p.x - origin.x + worldPosition.x,
                p.z - origin.z + worldPosition.y,   // Note: We use JSON 'z' for Java 'y'
                -p.y + origin.y + worldPosition.z  // Note: We use JSON 'y' for Java '-z'
        );

        currentMeshes.add(mesh);
      } catch (Exception e) {
        System.err.println("Failed to load part: " + e.getMessage());
      }
    }
  }
  protected Vector3f worldPosition = new Vector3f(0.0f,0.0f,0.0f);

  protected boolean checkPlace() {
    return true;
  }

  public void setWorldPosition(Vector3f snappedPos) {
    worldPosition.x = snappedPos.x;
    worldPosition.y = snappedPos.y;
    worldPosition.z = snappedPos.z;

  }

  public Vector3f getWorldPosition() {
    return new Vector3f(worldPosition);
  }

  public abstract void defaultSettings();

  public void changePosition(float x, float y, float z) {
    worldPosition.x += x;
    worldPosition.y += y;
    worldPosition.z += z;
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
    this.worldPosition.x = position.x;
    this.worldPosition.y = position.y;
    this.worldPosition.z = position.z;
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
    System.out.println(name.main + " placed in world at: " + worldPosition.x + ", " + worldPosition.z);
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


  protected void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) {
        System.err.println("Could not find file: " + jsonFilePath);
        return;
      }

      Gson gson = new Gson();
      Reader reader = new InputStreamReader(in);
      PlaceItemData data = gson.fromJson(reader, PlaceItemData.class);

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

  @Override public void cleanup() {
    for (Mesh mesh : currentMeshes) {
      mesh.cleanup();
    }
  }
  public void setupHitbox() {
    LevelData currentData = level.get(String.valueOf(currentLevel));
    if (currentData == null || currentData.make == null) return;

    this.boundingBox = null;

    for (MakeData make : currentData.make.values()) {
      // IMPORTANT: Ensure we use the String ID from the JSON pointer
      BaseModelParts part = baseModel.get(make.item.id);

      if (part == null) {
        System.err.println("Hitbox Error: Could not find part ID " + make.item.id + " in baseModel!");
        continue;
      }

      SeparatePartsLink link = part.separateParts.get(make.item.type);
      if (link != null && link.size != null) {
        BoundingBox partBox = getBoundingBox(make, link);
        if (this.boundingBox == null) {
          this.boundingBox = new BoundingBox(partBox.minX, partBox.maxX, partBox.minY, partBox.maxY, partBox.minZ, partBox.maxZ);
        } else {
          this.boundingBox.merge(partBox);
        }
      }
    }

    if (this.boundingBox != null) {
      System.out.println("Final Hitbox for " + name.main + ": Y=" + boundingBox.minY + " to " + boundingBox.maxY);
    }
  }

  public BoundingBox getBoundingBox() {

    if (this.boundingBox == null) {
      setupHitbox();
    }

    // Safety check: if setupHitbox STILL couldn't make a box (e.g. no size data in JSON)
    if (this.boundingBox == null) {
      // Return a tiny default box or a box at worldPosition so the game doesn't crash
      return new BoundingBox(worldPosition, new XYZMatrix());
    }
    return boundingBox;
  }
  private static @NotNull BoundingBox getBoundingBox(MakeData make, SeparatePartsLink link) {
    // If "pos" is missing in JSON, create a default (0,0,0) so it doesn't crash
    XYZMatrix pos = (make.pos != null) ? make.pos : new XYZMatrix();
    XYZMatrix origin = (link.origin != null) ? link.origin : new XYZMatrix();

    // Map Blender -> Java Y (Height)
    // Blender Z is height, Blender Y is depth (-Z)
    Vector3f partAnchor = new Vector3f(
            pos.x - origin.x,
            pos.z - origin.z,
            -pos.y + origin.y
    );

    return new BoundingBox(partAnchor, link.size);
  }

  public BoundingBox getBoundingBoxOffSet() {
    if (this.boundingBox == null){
      this.boundingBox = getBoundingBox();
    }
    return  new BoundingBox(
            boundingBox.minX + worldPosition.x, boundingBox.maxX + worldPosition.x, // X Pos
            boundingBox.minY + worldPosition.y, boundingBox.maxY + worldPosition.y, // Y Pos
            boundingBox.minZ + worldPosition.z, boundingBox.maxZ + worldPosition.z // Y Pos
            );
  }

  public Vector3f getPosition(){
    return worldPosition;
  }
}