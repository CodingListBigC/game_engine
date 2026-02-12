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
  public ShaderMaster(){
    this.shaderLoader = new ShaderLoader("shaders/vertex.glsl", "shaders/fragment.glsl");
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
  public void setUniform1i(String uniformName, int value) {
    // 1. Find the location of the uniform in the shader program
    int location = org.lwjgl.opengl.GL20.glGetUniformLocation(shaderLoader.getProgramId(), uniformName);

    // 2. Send the integer value to that location
    if (location != -1) {
      org.lwjgl.opengl.GL20.glUniform1i(location, value);
    } else {
      System.err.println("Could not find uniform: " + uniformName);
    }
  }
  public void setUniform1b(String uniformName, boolean value) {
    // 1. Get the location of the uniform in the compiled shader program
    int location = org.lwjgl.opengl.GL20.glGetUniformLocation(shaderLoader.getProgramId(), uniformName);

    // 2. Check if the uniform exists to avoid silent failures
    if (location != -1) {
      // 3. Convert boolean to 1 (true) or 0 (false) and send to GPU
      org.lwjgl.opengl.GL20.glUniform1i(location, value ? 1 : 0);
    } else {
      // Optional: Log a warning if you can't find the variable in the shader
      // System.err.println("Could not find uniform: " + uniformName);
    }
  }

  public void setUniformMat4f(String uniformName, Matrix4f modelMatrix) {
    int location = org.lwjgl.opengl.GL20.glGetUniformLocation(shaderLoader.getProgramId(), uniformName);
    if (location != -1){
      float[] matrixArray = new float[16];
      modelMatrix.get(matrixArray);
      org.lwjgl.opengl.GL20.glUniformMatrix4fv(location, false, matrixArray);
    }
  }
}