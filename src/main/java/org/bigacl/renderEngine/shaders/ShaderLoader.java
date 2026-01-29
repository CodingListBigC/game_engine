package org.bigacl.renderEngine.shaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class ShaderLoader {

  private final int programId;

  public ShaderLoader(String vertexShaderPath, String fragmentShaderPath) {
    // 1. Load the shader source from files
    try {
      String vertexSource = loadShaderSource(vertexShaderPath);
      String fragmentSource = loadShaderSource(fragmentShaderPath);

      // 2. Compile individual shaders
      int vertexShaderId = compileShader(vertexSource, GL_VERTEX_SHADER);
      int fragmentShaderId = compileShader(fragmentSource, GL_FRAGMENT_SHADER);

      // 3. Create a shader program and link the shaders
      programId = glCreateProgram();
      glAttachShader(programId, vertexShaderId);
      glAttachShader(programId, fragmentShaderId);
      glLinkProgram(programId);
      checkLinkingError(programId);

      // 4. Delete the individual shader objects after linking
      glDeleteShader(vertexShaderId);
      glDeleteShader(fragmentShaderId);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load shaders", e);
    }
  }

  public void use() {
    glUseProgram(programId);
  }
  private String loadShaderSource(String path) throws IOException {
    InputStream in = getClass().getClassLoader().getResourceAsStream(path);
    if (in == null) {
      throw new FileNotFoundException("Shader not found: " + path);
    }
    return new String(in.readAllBytes());
  }

  private int compileShader(String source, int type) {
    int shaderId = glCreateShader(type);
    glShaderSource(shaderId, source);
    glCompileShader(shaderId);
    checkCompilationError(shaderId);
    return shaderId;
  }

  private void checkCompilationError(int shaderId) {
    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
      System.err.println("ERROR: Shader Compilation Failed");
      System.err.println(glGetShaderInfoLog(shaderId, 1024));
      System.exit(-1);
    }
  }

  private void checkLinkingError(int programId) {
    if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
      System.err.println("ERROR: Program Linking Failed");
      System.err.println(glGetProgramInfoLog(programId, 1024));
      System.exit(-1);
    }
  }

  public int getProgramId() {
    return programId;
  }
}
