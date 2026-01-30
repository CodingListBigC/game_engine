package org.bigacl.renderEngine.mesh;

import org.bigacl.renderEngine.GameLogic;
import org.bigacl.renderEngine.Launcher;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.bigacl.renderEngine.texture.Texture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class Mesh {
  private int vao;
  private int vbo;
  private int ebo;
  private int tbo;  // Texture buffer
  private int indexCount;
  private float[] color;
  private ShaderMaster shader;
  private Texture texture;  // Add this
  public Mesh(int vao, int vbo, int ebo, int indexCount, int tbo) {
    this.vao = vao;
    this.vbo = vbo;
    this.ebo = ebo;
    this.tbo = tbo;
    this.indexCount = indexCount;
    this.color = new float[]{1.0f, 1.0f, 1.0f};
  }


  public void setTexture(Texture texture) {
    this.texture = texture;
  }

  public void render() {
    // 1. Get the shader instance
    ShaderMaster shader = Launcher.getShaderMaster();

    // 2. Safety Check: If the shader isn't ready, don't try to draw
    if (shader == null) {
      return;
    }

    // 3. Texture Setup
    if (texture != null) {
      // Activate texture unit 0
      org.lwjgl.opengl.GL13.glActiveTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0);
      texture.bind();
      // Tell the shader to use texture unit 0
      shader.setUniform1i("textureSampler", 0);
      shader.setUniform1b("useTexture", true);
    }else {
      shader.setUniform1b("useTexture", false);
    }

    // 4. Set Uniforms
    shader.setUniformVec3f("objectColor", color[0], color[1], color[2]);

    // 5. Draw the Geometry
    org.lwjgl.opengl.GL30.glBindVertexArray(vao);
    org.lwjgl.opengl.GL11.glDrawElements(org.lwjgl.opengl.GL11.GL_TRIANGLES, indexCount, org.lwjgl.opengl.GL11.GL_UNSIGNED_INT, 0);
    org.lwjgl.opengl.GL30.glBindVertexArray(0);

    // 6. Cleanup binding
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

}