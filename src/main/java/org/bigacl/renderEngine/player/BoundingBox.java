package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.gameItems.item.placeable.BasePlaceableItem;
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
  public BoundingBox(Vector3f partAnchor, BasePlaceableItem.XYZMatrix size) {
    // Blender X -> Java X (Width)
    // Blender Z -> Java Y (Height)
    // Blender Y -> Java Z (Depth)

    // If partAnchor is the CENTER of the part:
    float halfWidth = size.x / 2.0f;
    float halfDepth = size.y / 2.0f;

    this.minX = partAnchor.x - halfWidth;
    this.maxX = partAnchor.x + halfWidth;

    // Height: Usually building origins are at the bottom (feet),
    // so we just add the height (size.z) to the anchor's Y.
    this.minY = partAnchor.y;
    this.maxY = partAnchor.y + size.z;

    this.minZ = partAnchor.z - halfDepth;
    this.maxZ = partAnchor.z + halfDepth;
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

  public void log(String s) {
    System.out.print(s);
    System.out.print("minX: " + this.minX);
    System.out.print("maxX: " + this.maxX);
    System.out.print("minY: " + this.minY);
    System.out.print("maxY: " + this.maxY);
    System.out.print("minZ: " + this.minZ);
    System.out.print("maxZ: " + this.maxZ);
    System.out.println("");
  }
  public boolean intersects(BoundingBox other) {
    // Check if there is an overlap on all three axes
    return (this.minX <= other.maxX && this.maxX >= other.minX) &&
            (this.minY <= other.maxY && this.maxY >= other.minY) &&
            (this.minZ <= other.maxZ && this.maxZ >= other.minZ);
  }

}