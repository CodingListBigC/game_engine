package org.bigacl.renderEngine.model;

import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.utils.number.rotation.Rotation;
import org.bigacl.renderEngine.utils.number.rotation.RotationXYZ;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelAbstract {
  // Transformation Data
  protected Vector3f worldPosition = new Vector3f(0.0f, 0.0f, 0.0f);
  protected RotationXYZ rotation = new RotationXYZ(new Rotation(0));
  protected float scale = 1.0f;
  protected Matrix4f modelMatrix = new Matrix4f();
  protected List<Mesh> currentMeshes = new ArrayList<>();
  protected String unit;

  /**
   * Combines position, rotation, and scale into one matrix for the GPU.
   */
  protected void updateModelMatrix() {
    this.modelMatrix.identity()
            .translate(worldPosition)
            .rotateX((float) Math.toRadians(rotation.getX()))
            .rotateY((float) Math.toRadians(rotation.getY()))
            .rotateZ((float) Math.toRadians(rotation.getZ()))
            .scale(scale);
  }

  public void changePosition(float x, float y, float z) {
    worldPosition.add(x, y, z);
    updateModelMatrix(); // Rebuild matrix so the move shows up on screen
  }

  public void setWorldPosition(Vector3f snappedPos) {
    try {
      worldPosition.set(snappedPos);
    } catch (Exception e) {
    }
    updateModelMatrix();
  }

  public Vector3f getRotation() {
    return rotation.getVector();
  }

  public void setRotation(Vector3f rotationVector) {
    rotation.setVector(rotationVector);
  }

  public void cleanup() {
    for (Mesh m : currentMeshes) m.cleanup();
  }

  protected abstract void loadData();
}

