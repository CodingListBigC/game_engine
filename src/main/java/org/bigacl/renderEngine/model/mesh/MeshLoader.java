package org.bigacl.renderEngine.model.mesh;

import org.bigacl.renderEngine.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MeshLoader {
  private static List<Integer> vaos = new ArrayList<>();
  private static List<Integer> vbos = new ArrayList<>();

  public static Mesh createMesh(float[] positions, int[] indices, float[] texCords, MeshSize meshSize) {
    int vao = genVAO();
    int vbo = storeData(0, 3, positions);
    int tbo = storeData(1,2, texCords);
    int ebo = bindIndices(indices);
    glBindVertexArray(0);
    return new Mesh(vao, vbo, ebo, indices.length, tbo, meshSize);
  }

  private static int genVAO() {
    int vao = glGenVertexArrays();
    vaos.add(vao);
    glBindVertexArray(vao);
    return vao;
  }

  private static int storeData(int attribute, int size, float[] data) {
    int vbo = glGenBuffers();
    vbos.add(vbo);
    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    FloatBuffer buffer = BufferUtils.createFloatBuffer(data);
    glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    BufferUtils.freeBuffer(buffer);

    glVertexAttribPointer(attribute, size, GL_FLOAT, false, 0, 0);
    glEnableVertexAttribArray(attribute);

    glBindBuffer(GL_ARRAY_BUFFER, 0);

    return vbo;
  }

  private static int bindIndices(int[] indices) {
    int ebo = glGenBuffers();
    vbos.add(ebo);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

    IntBuffer buffer = BufferUtils.createIntBuffer(indices);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    BufferUtils.freeBuffer(buffer);

    return ebo;
  }

  public static void cleanup() {
    for (int vao : vaos) {
      glDeleteVertexArrays(vao);
    }
    for (int vbo : vbos) {
      glDeleteBuffers(vbo);
    }
    vaos.clear();
    vbos.clear();
  }
}