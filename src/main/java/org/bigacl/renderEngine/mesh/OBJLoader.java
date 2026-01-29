package org.bigacl.renderEngine.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

  public static Mesh loadOBJ(String filePath) {
    List<Vector3f> vertices = new ArrayList<>();
    List<Vector2f> textures = new ArrayList<>();
    List<Vector3f> normals = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();

    float[] verticesArray = null;
    float[] texturesArray = null;
    float[] normalsArray = null;
    int[] indicesArray = null;

    try {
      InputStream in = OBJLoader.class.getClassLoader().getResourceAsStream(filePath);
      if (in == null) {
        throw new RuntimeException("OBJ file not found: " + filePath);
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;

      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(" ");

        switch (tokens[0]) {
          case "v":
            // Vertex position
            Vector3f vertex = new Vector3f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2]),
                    Float.parseFloat(tokens[3])
            );
            vertices.add(vertex);
            break;

          case "vt":
            // Texture coordinate
            Vector2f texture = new Vector2f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2])
            );
            textures.add(texture);
            break;

          case "vn":
            // Normal
            Vector3f normal = new Vector3f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2]),
                    Float.parseFloat(tokens[3])
            );
            normals.add(normal);
            break;

          case "f":
            // Face
            processFace(tokens, indices);
            break;

          default:
            break;
        }
      }
      reader.close();

    } catch (Exception e) {
      throw new RuntimeException("Error loading OBJ file: " + filePath, e);
    }

    // Convert lists to arrays
    verticesArray = new float[vertices.size() * 3];
    indicesArray = new int[indices.size()];

    int vertexPointer = 0;
    for (Vector3f vertex : vertices) {
      verticesArray[vertexPointer++] = vertex.x;
      verticesArray[vertexPointer++] = vertex.y;
      verticesArray[vertexPointer++] = vertex.z;
    }

    for (int i = 0; i < indices.size(); i++) {
      indicesArray[i] = indices.get(i);
    }

    return MeshLoader.createMesh(verticesArray, indicesArray);
  }

  private static void processFace(String[] tokens, List<Integer> indices) {
    // Process triangle face (f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3)
    for (int i = 1; i < tokens.length; i++) {
      String[] parts = tokens[i].split("/");
      int vertexIndex = Integer.parseInt(parts[0]) - 1;  // OBJ indices start at 1
      indices.add(vertexIndex);
    }
  }
}