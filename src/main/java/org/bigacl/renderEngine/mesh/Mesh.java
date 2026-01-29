package org.bigacl.renderEngine.mesh;

import org.bigacl.renderEngine.Launcher;
import org.bigacl.renderEngine.shaders.ShaderMaster;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class Mesh {
  private int vao;
  private int vbo;
  private int ebo;
  private int vertexCount;
  private float[] color;
  public Mesh(int vao,int vbo, int ebo, int vertexCount) {
    this.vao = vao;
    this.vbo = vbo;
    this.ebo = ebo;
    this.vertexCount = vertexCount;
    this.color = new float[]{1.0f, 1.0f, 1.0f};
  }

  public int getVaoID() {
    return vao;
  }

  public int getVertexCount() {
    return vertexCount;
  }

  public void cleanup(){
    glDeleteVertexArrays(vao);
    glDeleteBuffers(vbo);
    glDeleteBuffers(ebo);
  }

  public void setColor(float r, float g, float b){
    this.color[0] = r;
    this.color[1] = g;
    this.color[2] = b;
  }
  public void render() {
    ShaderMaster shaderMaster = Launcher.getShaderMaster();
    if (shaderMaster != null) {
      shaderMaster.setUniformVec3f("objectColor", color[0], color[1], color[2]);
    }
    // Bind the VAO
    GL30.glBindVertexArray(vao);
    int error = GL11.glGetError();
    if (error != GL11.GL_NO_ERROR) {
      System.out.println("OpenGL Error before draw: " + error);
    }
    // Draw using indices
    GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
    error = GL11.glGetError();
    if (error != GL11.GL_NO_ERROR) {
      System.out.println("OpenGL Error before draw: " + error);
    }
    // Unbind the VAO
    GL30.glBindVertexArray(0);
  }
}
