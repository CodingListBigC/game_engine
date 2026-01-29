package org.bigacl.renderEngine.utils;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
  public static FloatBuffer createFloatBuffer(float[] data) {
    FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  public static FloatBuffer createFloatBuffer(int size) {
    return MemoryUtil.memAllocFloat(size);
  }

  public static IntBuffer createIntBuffer(int[] data) {
    IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  // Helper method to free buffers when done
  public static void freeBuffer(FloatBuffer buffer) {
    MemoryUtil.memFree(buffer);
  }

  public static void freeBuffer(IntBuffer buffer) {
    MemoryUtil.memFree(buffer);
  }
}