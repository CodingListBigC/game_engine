package org.bigacl.renderEngine.gameItems.item.placeable;

import com.google.gson.Gson;
import org.bigacl.renderEngine.gameItems.item.ItemInterface;
import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.model.mesh.OBJLoader;
import org.bigacl.renderEngine.player.BoundingBox;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.model.texture.Texture;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePlaceableItem implements ItemInterface, PlaceableInterface {

  // Transformation Data
  protected Vector3f worldPosition = new Vector3f(0.0f, 0.0f, 0.0f);
  protected Vector3f rotation = new Vector3f(0.0f, 0.0f, 0.0f);
  protected float scale = 1.0f;
  protected Matrix4f modelMatrix = new Matrix4f();

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

  /**
   * Loads the model parts. Positions are set RELATIVE to 0,0,0.
   * The modelMatrix handles moving them to worldPosition.
   */
  protected void loadModel() {
    String levelKey = String.valueOf(this.currentLevel);
    LevelData levelData = this.level.get(levelKey);

    if (levelData == null || levelData.make == null) return;

    currentMeshes.clear();

    for (MakeData placement : levelData.make.values()) {
      try {
        BaseModelParts partConfig = this.baseModel.get(placement.item.id);
        if (partConfig == null) continue;

        SeparatePartsLink link = partConfig.separateParts.get(placement.item.type);
        if (link == null) continue;

        Mesh mesh = OBJLoader.loadOBJ(folderPath + "/" + link.model);
        mesh.setTexture(new Texture(folderPath + "/" + link.texture));

        XYZMatrix p = (placement.pos != null) ? placement.pos : new XYZMatrix();
        XYZMatrix origin = (link.origin != null) ? link.origin : new XYZMatrix();

        // LOCAL POSITION (Removed worldPosition here)
        mesh.setPosition(
                p.x - origin.x,
                p.z - origin.z,
                -p.y + origin.y
        );

        currentMeshes.add(mesh);
      } catch (Exception e) {
        System.err.println("Failed to load part: " + e.getMessage());
      }
    }
    updateModelMatrix();
  }

  /**
   * Combines position, rotation, and scale into one matrix for the GPU.
   */
  protected void updateModelMatrix() {
    this.modelMatrix.identity()
            .translate(worldPosition)
            .rotateX((float) Math.toRadians(rotation.x))
            .rotateY((float) Math.toRadians(rotation.y))
            .rotateZ((float) Math.toRadians(rotation.z))
            .scale(scale);
  }

  public void changePosition(float x, float y, float z) {
    worldPosition.add(x, y, z);
    updateModelMatrix(); // Rebuild matrix so the move shows up on screen
  }

  public void setWorldPosition(Vector3f snappedPos) {
    worldPosition.set(snappedPos);
    updateModelMatrix();
  }

  @Override
  public void render() {
    if (!isPlaced) return;

    if (currentMeshes.isEmpty()) {
      loadModel();
    }

    ShaderMaster shader = ClassConst.shader3d;
    // 1. Send the matrix once for the whole object
    shader.setUniformMatrix4f("modelMatrix", this.modelMatrix);

    // 2. Draw all parts (they use the matrix above in the shader)
    for (Mesh mesh : currentMeshes) {
      mesh.render(modelMatrix);
    }
  }

  public String mainName() {
    return name.main;
  }

  // --- Data Structures for GSON ---
  public static class NameData { public String main, plural; }
  public static class BaseModelParts {
    public String partName;
    public int defaultFacing;
    public Map<String, SeparatePartsLink> separateParts;
  }
  public static class SeparatePartsLink {
    public String name, model, texture;
    public XYZMatrix size, origin;
  }
  public static class LevelData {
    public int price, requiredLevel;
    public Map<String, MakeData> make;
  }
  public static class MakeData { public ItemPointer item; public XYZMatrix pos; }
  public static class ItemPointer { public String id, type; }
  public static class XYZMatrix { public float x = 0.0f, y = 0.0f, z = 0.0f; }

  // --- Interface & Lifecycle Methods ---
  @Override
  public void place(Vector3f position, float rotation) {
    this.worldPosition.set(position);
    this.rotation.y = rotation;
    this.isPlaced = true;
    loadModel();
    setupHitbox();
  }
  protected void loadData() {
    String jsonFilePath = folderPath + "/" + jsonName;
    try (InputStream in = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
      if (in == null) return;

      Gson gson = new Gson();
      InputStreamReader reader = new InputStreamReader(in);

      // 1. Load the raw data from JSON
      PlaceItemData data = gson.fromJson(reader, PlaceItemData.class);

      if (data != null) {
        this.name = data.name;
        this.amount_of_levels = data.amount_of_levels;
        this.type = data.type;

        // 2. CRITICAL: Re-serialize and de-serialize the Maps
        // This creates a "Deep Copy" so that this instance has its OWN maps in memory
        String baseModelJson = gson.toJson(data.baseModel);
        this.baseModel = gson.fromJson(baseModelJson, new com.google.gson.reflect.TypeToken<Map<String, BaseModelParts>>(){}.getType());

        String levelJson = gson.toJson(data.level);
        this.level = gson.fromJson(levelJson, new com.google.gson.reflect.TypeToken<Map<String, LevelData>>(){}.getType());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setupHitbox() {
    LevelData currentData = level.get(String.valueOf(currentLevel));
    if (currentData == null || currentData.make == null) return;
    this.boundingBox = null;
    for (MakeData make : currentData.make.values()) {
      BaseModelParts part = baseModel.get(make.item.id);
      if (part == null) continue;
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
  }

  public BoundingBox getBoundingBox() {
    if (this.boundingBox == null) setupHitbox();
    return (this.boundingBox != null) ? this.boundingBox : new BoundingBox(worldPosition, new XYZMatrix());
  }

  private static @NotNull BoundingBox getBoundingBox(MakeData make, SeparatePartsLink link) {
    XYZMatrix pos = (make.pos != null) ? make.pos : new XYZMatrix();
    XYZMatrix origin = (link.origin != null) ? link.origin : new XYZMatrix();
    Vector3f partAnchor = new Vector3f(pos.x - origin.x, pos.z - origin.z, -pos.y + origin.y);
    return new BoundingBox(partAnchor, link.size);
  }

  public BoundingBox getBoundingBoxOffSet() {
    BoundingBox base = getBoundingBox();
    return new BoundingBox(
            base.minX + worldPosition.x, base.maxX + worldPosition.x,
            base.minY + worldPosition.y, base.maxY + worldPosition.y,
            base.minZ + worldPosition.z, base.maxZ + worldPosition.z
    );
  }

  @Override public void cleanup() { for (Mesh m : currentMeshes) m.cleanup(); }
  @Override public void init() {}
  @Override public void leftClick() {}
  @Override public void rightClick() {}
  @Override public boolean checkPlace(Vector3f position, float rotation) { return true; }
  public abstract void defaultSettings();
  public Vector3f getWorldPosition() { return new Vector3f(worldPosition); }
  public Vector3f getPosition() { return worldPosition; }
}