package org.bigacl.renderEngine.mesh;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.texture.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class Mesh {
  private int vao;
  private int vbo;
  private int ebo;
  private int tbo;  // Texture buffer
  private final int indexCount;
  private final float[] color;
  private Texture texture;
  private final MeshSize meshSize;

  // Position and Rotation storage
  private final Vector3f position;
  private final Vector3f rotation;

  public Mesh(int vao, int vbo, int ebo, int indexCount, int tbo, MeshSize meshSize) {
    this.vao = vao;
    this.vbo = vbo;
    this.ebo = ebo;
    this.tbo = tbo;
    this.indexCount = indexCount;
    this.color = new float[]{1.0f, 1.0f, 1.0f};
    this.meshSize = meshSize;

    // Initialize at zero
    this.position = new Vector3f(0, 0, 0);
    this.rotation = new Vector3f(0, 0, 0);
  }

  /**
   * Sets the world position of this specific mesh part.
   */
  public void setPosition(float x, float y, float z) {
    this.position.set(x, y, z);
  }

  /**
   * Sets the rotation (in degrees) for the mesh.
   */
  public void setRotation(float x, float y, float z) {
    this.rotation.set(x, y, z);
  }

  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public void render(Matrix4f modelMatrix) {
    // 1. Get the shader instance
    ShaderMaster shader = ClassConst.shader3d;

    // 2. Safety Check
    if (shader == null) {
      return;
    }

    // 3. Texture Setup
    if (texture != null) {
      org.lwjgl.opengl.GL13.glActiveTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0);
      texture.bind();
      shader.setUniform1i("textureSampler", 0);
      shader.setUniform1b("useTexture", true);
    } else {
      shader.setUniform1b("useTexture", false);
    }


    shader.setUniformMatrix4f("modelMatrix", modelMatrix);
    // 5. Set Color Uniform
    shader.setUniformVec3f("objectColor", color[0], color[1], color[2]);

    // 6. Draw the Geometry
    org.lwjgl.opengl.GL30.glBindVertexArray(vao);
    org.lwjgl.opengl.GL11.glDrawElements(org.lwjgl.opengl.GL11.GL_TRIANGLES, indexCount, org.lwjgl.opengl.GL11.GL_UNSIGNED_INT, 0);
    org.lwjgl.opengl.GL30.glBindVertexArray(0);

    // 7. Cleanup binding
    if (texture != null) {
      texture.unbind();
    }
  }

  public void cleanup() {
    if (vao != 0) {
      glDeleteVertexArrays(vao);
      vao = 0;
    }
    if (vbo != 0) {
      glDeleteBuffers(vbo);
      vbo = 0;
    }
    if (ebo != 0) {
      glDeleteBuffers(ebo);
      ebo = 0;
    }
    if (tbo != 0) {
      glDeleteBuffers(tbo);
      tbo = 0;
    }
    if (texture != null) {
      texture.cleanup();
    }
  }

  public void setColor(float r, float g, float b){
    color[0] = r;
    color[1] = g;
    color[2] = b;
  }

  public void printSize() {
    meshSize.printSize();
  }

  // Getters in case you need to check coordinates later
  public Vector3f getPosition() { return position; }
  public Vector3f getRotation() { return rotation; }
}