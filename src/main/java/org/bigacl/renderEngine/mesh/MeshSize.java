package org.bigacl.renderEngine.mesh;

import org.joml.Vector3f;

public class MeshSize {
  private final float width;//X
  private final float height;// Y
  private final float depth;// Z

  public MeshSize(float width, float height, float depth) {
    this.width = width;
    this.height = height;
    this.depth= depth;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getDepth() {
    return depth;
  }

  public Vector3f getSize() {
    return new Vector3f(width,height,depth);
  }

  public void printSize() {
   System.out.println("Mesh Size: x:" +width + ", y:" + height + ", z:" + depth);
  }
}
