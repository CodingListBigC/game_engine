package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.joml.Vector3f;

public class BoundingBox {
  public float minX, maxX, minY, maxY, minZ, maxZ;

  public BoundingBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.minZ = minZ;
    this.maxZ = maxZ;
  }
  // Use ONLY local pos and size. worldPos is added later during checks.
  public BoundingBox(Vector3f localPos, BasePlaceableItem.XYZMatrix size) {
    // Blender X -> Java X (Width)
    // Blender Z -> Java Y (Height)
    // Blender Y -> Java Z (Depth)

    float halfWidth = size.x / 2.0f;
    float halfDepth = size.y / 2.0f;

    this.minX = localPos.x - halfWidth;
    this.maxX = localPos.x + halfWidth;

    // Height usually starts at the feet (localPos.y) and goes up
    this.minY = localPos.y;
    this.maxY = localPos.y + size.z;

    this.minZ = localPos.z - halfDepth;
    this.maxZ = localPos.z + halfDepth;
  }

  /**
   * Corrected Merge Method
   * This expands this box to enclose the other box.
   */
  public void merge(BoundingBox other) {
    this.minX = Math.min(this.minX, other.minX);
    this.maxX = Math.max(this.maxX, other.maxX);

    this.minY = Math.min(this.minY, other.minY);
    this.maxY = Math.max(this.maxY, other.maxY);

    this.minZ = Math.min(this.minZ, other.minZ);
    this.maxZ = Math.max(this.maxZ, other.maxZ);
  }
  public float getWorldMinX(float worldX) { return this.minX + worldX; }
  public float getWorldMaxX(float worldX) { return this.maxX + worldX; }
  /**
   * Checks collision while accounting for world position.
   */
  public boolean intersects(Vector3f myWorldPos, BoundingBox other, Vector3f otherWorldPos) {
    return (this.minX + myWorldPos.x <= other.maxX + otherWorldPos.x &&
            this.maxX + myWorldPos.x >= other.minX + otherWorldPos.x) &&
            (this.minY + myWorldPos.y <= other.maxY + otherWorldPos.y &&
                    this.maxY + myWorldPos.y >= other.minY + otherWorldPos.y) &&
            (this.minZ + myWorldPos.z <= other.maxZ + otherWorldPos.z &&
                    this.maxZ + myWorldPos.z >= other.minZ + otherWorldPos.z);
  }
}