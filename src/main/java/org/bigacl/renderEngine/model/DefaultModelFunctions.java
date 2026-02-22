package org.bigacl.renderEngine.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class DefaultModelFunctions {
  public static Matrix4f getPlainModelMatrix(Vector3f position, Vector3f rotation){
    // 4. Set Transformation Uniforms
    // Import org.joml.Matrix4f;
    Matrix4f modelMatrix = new Matrix4f()
            .translation(position)
            .rotateXYZ((float)Math.toRadians(rotation.x),
                    (float)Math.toRadians(rotation.y),
                    (float)Math.toRadians(rotation.z));

    return modelMatrix;
  }
}
