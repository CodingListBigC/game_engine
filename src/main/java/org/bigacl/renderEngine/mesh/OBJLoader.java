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
    List<Integer> textureIndices = new ArrayList<>();

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
            // Face - handle v/vt/vn format
            processFace(tokens, indices, textureIndices);
            break;

          default:
            break;
        }
      }
      reader.close();

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error loading OBJ file: " + filePath, e);
    }

    // Convert lists to arrays
    float[] verticesArray = new float[vertices.size() * 3];
    float[] texCoordsArray = new float[vertices.size() * 2];
    int[] indicesArray = new int[indices.size()];

    int vertexPointer = 0;
    for (Vector3f vertex : vertices) {
      verticesArray[vertexPointer++] = vertex.x;
      verticesArray[vertexPointer++] = vertex.y;
      verticesArray[vertexPointer++] = vertex.z;
    }

    // Reorder texture coordinates to match vertices
    for (int i = 0; i < indices.size(); i++) {
      int currentVertexIndex = indices.get(i);
      int currentTexIndex = textureIndices.get(i);

      if (currentTexIndex >= 0 && currentTexIndex < textures.size()) {
        Vector2f currentTex = textures.get(currentTexIndex);
        texCoordsArray[currentVertexIndex * 2] = currentTex.x;
        texCoordsArray[currentVertexIndex * 2 + 1] = 1.0f - currentTex.y;  // Flip Y for OpenGL
      }
    }

    for (int i = 0; i < indices.size(); i++) {
      indicesArray[i] = indices.get(i);
    }

    System.out.println("Loaded OBJ: " + vertices.size() + " vertices, " +
            textures.size() + " tex coords, " + indices.size() + " indices");

    return MeshLoader.createMesh(verticesArray, indicesArray, texCoordsArray);
  }

  private static void processFace(String[] tokens, List<Integer> indices, List<Integer> textureIndices) {
    int vertexCount = tokens.length - 1;

    if (vertexCount == 3) {
      // Triangle
      for (int i = 1; i <= 3; i++) {
        String[] parts = tokens[i].split("/");
        int vertexIndex = Integer.parseInt(parts[0]) - 1;
        indices.add(vertexIndex);

        // Add texture index if it exists
        if (parts.length > 1 && !parts[1].isEmpty()) {
          textureIndices.add(Integer.parseInt(parts[1]) - 1);
        } else {
          textureIndices.add(0);
        }
      }
    } else if (vertexCount == 4) {
      // Quad - split into two triangles
      int[] quadVertices = new int[4];
      int[] quadTextures = new int[4];

      for (int i = 0; i < 4; i++) {
        String[] parts = tokens[i + 1].split("/");
        quadVertices[i] = Integer.parseInt(parts[0]) - 1;

        if (parts.length > 1 && !parts[1].isEmpty()) {
          quadTextures[i] = Integer.parseInt(parts[1]) - 1;
        } else {
          quadTextures[i] = 0;
        }
      }

      // First triangle: 0, 1, 2
      indices.add(quadVertices[0]);
      indices.add(quadVertices[1]);
      indices.add(quadVertices[2]);
      textureIndices.add(quadTextures[0]);
      textureIndices.add(quadTextures[1]);
      textureIndices.add(quadTextures[2]);

      // Second triangle: 0, 2, 3
      indices.add(quadVertices[0]);
      indices.add(quadVertices[2]);
      indices.add(quadVertices[3]);
      textureIndices.add(quadTextures[0]);
      textureIndices.add(quadTextures[2]);
      textureIndices.add(quadTextures[3]);
    }
  }
}