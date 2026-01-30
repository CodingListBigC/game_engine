package org.bigacl.renderEngine.texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
  private int textureId;
  private int width;
  private int height;

  public Texture(String filePath) {
    textureId = loadTexture(filePath);
  }

  private int loadTexture(String filePath) {
    ByteBuffer imageBuffer;
    int width, height;

    try (MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer channels = stack.mallocInt(1);

      // Load image
      String path = "textures/" + filePath;
      STBImage.stbi_set_flip_vertically_on_load(true);
      imageBuffer = STBImage.stbi_load_from_memory(
              loadResource(path),
              w, h, channels, 4
      );

      if (imageBuffer == null) {
        throw new RuntimeException("Failed to load texture: " + filePath +
                " - " + STBImage.stbi_failure_reason());
      }

      width = w.get();
      height = h.get();
      this.width = width;
      this.height = height;
    }

    // Create OpenGL texture
    int textureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, textureId);

    // Set texture parameters
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    // Upload texture data
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,
            0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
    glGenerateMipmap(GL_TEXTURE_2D);

    // Free image buffer
    STBImage.stbi_image_free(imageBuffer);

    System.out.println("Loaded texture: " + filePath + " (" + width + "x" + height + ")");

    return textureId;
  }

  private ByteBuffer loadResource(String path) {
    try {
      var in = getClass().getClassLoader().getResourceAsStream(path);
      if (in == null) {
        throw new RuntimeException("Resource not found: " + path);
      }
      byte[] bytes = in.readAllBytes();
      ByteBuffer buffer = org.lwjgl.BufferUtils.createByteBuffer(bytes.length);
      buffer.put(bytes);
      buffer.flip();
      return buffer;
    } catch (Exception e) {
      throw new RuntimeException("Failed to load resource: " + path, e);
    }
  }

  public void bind() {
    glBindTexture(GL_TEXTURE_2D, textureId);
  }

  public void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  public void cleanup() {
    glDeleteTextures(textureId);
  }

  public int getTextureId() {
    return textureId;
  }
}