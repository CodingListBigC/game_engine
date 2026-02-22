package org.bigacl.renderEngine.model.mesh;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

  public static Mesh loadOBJ(String filePath) {
    List<Vector3f> rawVertices = new ArrayList<>();
    List<Vector2f> rawTextures = new ArrayList<>();
    List<Vector3f> rawNormals = new ArrayList<>();

    // These lists will store the final, reordered data
    List<Float> verticesList = new ArrayList<>();
    List<Float> texCoordsList = new ArrayList<>();
    List<Integer> indicesList = new ArrayList<>();

    // Bounds for MeshSize
    float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
    float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
    float minZ = Float.MAX_VALUE, maxZ = Float.MIN_VALUE;

    try {
      InputStream in = OBJLoader.class.getClassLoader().getResourceAsStream(filePath);
      if (in == null) {
        throw new RuntimeException("OBJ file not found: " + filePath);
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;

      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("\\s+"); // Handles multiple spaces
        if (tokens.length < 1) continue;

        switch (tokens[0]) {
          case "v":
            Vector3f vertex = new Vector3f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2]),
                    Float.parseFloat(tokens[3])
            );
            rawVertices.add(vertex);
            // Calculate bounds
            if (vertex.x < minX) minX = vertex.x;
            if (vertex.x > maxX) maxX = vertex.x;
            if (vertex.y < minY) minY = vertex.y;
            if (vertex.y > maxY) maxY = vertex.y;
            if (vertex.z < minZ) minZ = vertex.z;
            if (vertex.z > maxZ) maxZ = vertex.z;
            break;

          case "vt":
            rawTextures.add(new Vector2f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2])
            ));
            break;

          case "vn":
            rawNormals.add(new Vector3f(
                    Float.parseFloat(tokens[1]),
                    Float.parseFloat(tokens[2]),
                    Float.parseFloat(tokens[3])
            ));
            break;

          case "f":
            // Process the face and build the final lists immediately
            processFace(tokens, rawVertices, rawTextures, verticesList, texCoordsList, indicesList);
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

    // Convert Lists to primitive arrays for the Mesh
    float[] verticesArray = new float[verticesList.size()];
    for (int i = 0; i < verticesList.size(); i++) verticesArray[i] = verticesList.get(i);

    float[] texCoordsArray = new float[texCoordsList.size()];
    for (int i = 0; i < texCoordsList.size(); i++) texCoordsArray[i] = texCoordsList.get(i);

    int[] indicesArray = new int[indicesList.size()];
    for (int i = 0; i < indicesList.size(); i++) indicesArray[i] = indicesList.get(i);

    float width  = maxX - minX;
    float height = maxY - minY;
    float depth  = maxZ - minZ;

    return MeshLoader.createMesh(verticesArray, indicesArray, texCoordsArray, new MeshSize(width, height, depth));
  }

  private static void processFace(String[] tokens, List<Vector3f> rawV, List<Vector2f> rawT,
                                  List<Float> finalV, List<Float> finalT, List<Integer> finalI) {

    // This helper converts the "v/vt/vn" string into actual final vertex data
    // We use a simple triangulation: every 3 points in a face = 1 triangle
    int numVertices = tokens.length - 1;

    // Triangulation for quads or polygons (Fans out from the first vertex)
    for (int i = 2; i < numVertices; i++) {
      addVertex(tokens[1], rawV, rawT, finalV, finalT, finalI); // First vertex
      addVertex(tokens[i], rawV, rawT, finalV, finalT, finalI); // Previous vertex
      addVertex(tokens[i + 1], rawV, rawT, finalV, finalT, finalI); // Current vertex
    }
  }

  private static void addVertex(String token, List<Vector3f> rawV, List<Vector2f> rawT,
                                List<Float> finalV, List<Float> finalT, List<Integer> finalI) {

    String[] parts = token.split("/");

    // Vertex Index (OBJ is 1-based)
    int vIdx = Integer.parseInt(parts[0]) - 1;
    Vector3f v = rawV.get(vIdx);
    finalV.add(v.x);
    finalV.add(v.y);
    finalV.add(v.z);

    // Texture Index
    if (parts.length > 1 && !parts[1].isEmpty()) {
      int tIdx = Integer.parseInt(parts[1]) - 1;
      Vector2f t = rawT.get(tIdx);
      finalT.add(t.x);
      finalT.add(1.0f - t.y); // Flipped for OpenGL
    } else {
      finalT.add(0.0f);
      finalT.add(0.0f);
    }

    // Add index (since we are duplicating vertices for simplicity,
    // the index is just the current size of the list / 3)
    finalI.add((finalV.size() / 3) - 1);
  }
}