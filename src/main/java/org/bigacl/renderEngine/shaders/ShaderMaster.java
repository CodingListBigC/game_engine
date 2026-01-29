package org.bigacl.renderEngine.shaders;

import org.bigacl.renderEngine.utils.BufferUtils;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class ShaderMaster {
  private final ShaderLoader shaderLoader;

  public ShaderMaster(String vertexShaderPath, String fragmentShaderPath) {
    this.shaderLoader = new ShaderLoader(vertexShaderPath, fragmentShaderPath);
  }

  public void bind() {
    shaderLoader.use();
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void cleanup() {
    glDeleteProgram(shaderLoader.getProgramId());
  }
  public void setUniformMatrix4f(String name, Matrix4f matrix) {
    int location = glGetUniformLocation(shaderLoader.getProgramId(), name);
    if (location == -1) {
      System.err.println("WARNING: Uniform '" + name + "' not found in shader!");
      return;
    }
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    matrix.get(buffer);
    glUniformMatrix4fv(location, false, buffer);
    BufferUtils.freeBuffer(buffer);
  }
  public void setUniformVec3f(String name, float x, float y, float z) {
    int location = glGetUniformLocation(shaderLoader.getProgramId(), name);
    if (location == -1) {
      System.err.println("WARNING: Uniform '" + name + "' not found in shader!");
      return;
    }
    glUniform3f(location, x, y, z);
  }
}