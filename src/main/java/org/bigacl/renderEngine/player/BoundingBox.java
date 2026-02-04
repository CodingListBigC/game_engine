package org.bigacl.renderEngine.player;

import org.bigacl.renderEngine.item.placeable.BasePlaceableItem;
import org.joml.Vector3f;

public class BoundingBox {
  public float minX, maxX, minY, maxY, minZ, maxZ;

  public BoundingBox(Vector3f pos, BasePlaceableItem.XYZMatrix size) {
    // size.x, size.z, size.y corresponds to Java width, height, depth
    // based on your Blender-to-Java swap logic.
    this.minX = pos.x - (size.x / 2);
    this.maxX = pos.x + (size.x / 2);
    this.minY = pos.y; // Base of the object at position.y
    this.maxY = pos.y + size.z; // Blender Z is Java Y (height)
    this.minZ = pos.z - (size.y / 2); // Blender Y is Java Z (depth)
    this.maxZ = pos.z + (size.y / 2);
  }

  public boolean intersects(BoundingBox other) {
    return (this.minX <= other.maxX && this.maxX >= other.minX) &&
            (this.minY <= other.maxY && this.maxY >= other.minY) &&
            (this.minZ <= other.maxZ && this.maxZ >= other.minZ);
  }
  public void merge(BoundingBox other) {
    this.minX = Math.min(this.minX, other.minX);
    this.maxX = Math.max(this.maxX, other.maxX);
    this.minY = Math.min(this.minY, other.minY);
    this.maxY = Math.max(this.maxY, other.maxY);
    this.minZ = Math.min(this.minZ, other.minZ);
    this.maxZ = Math.max(this.maxZ, other.maxZ);
  }
}