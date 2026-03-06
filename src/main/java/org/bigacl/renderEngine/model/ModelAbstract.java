package org.bigacl.renderEngine.model;

import org.bigacl.renderEngine.model.mesh.Mesh;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.number.rotation.RotationXYZ;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelAbstract {
  // Transformation Data
  protected Vector3f worldPosition = new Vector3f(0.0f, 0.0f, 0.0f);
  protected RotationXYZ rotation;
  protected float scale = 1.0f;
  protected Matrix4f modelMatrix = new Matrix4f();
  protected List<Mesh> currentMeshes = new ArrayList<>();

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

  public void cleanup() {
    for (Mesh m : currentMeshes) m.cleanup();
  }
}

