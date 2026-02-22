package org.bigacl.renderEngine.window;

import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class WindowMaster {
  long window;
  int mouseAction = 0;


  public void init() {
    // Using X11 instead of Wayland
    glfwInitHint(GLFW_PLATFORM, GLFW_PLATFORM_X11);
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
    glfwDefaultWindowHints(); // set default window hints
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // window will be resizable
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    window = glfwCreateWindow(1600, 900, Const.WINDOW_TITLE, NULL, NULL);
    if (window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }
    glfwSetKeyCallback(window, (windowHandle, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(windowHandle, true);
      }
    });
    // Make the OpenGL context current
    glfwMakeContextCurrent(window);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);

    // This is critical - creates the OpenGL capabilities instance
    GL.createCapabilities();

    glEnable(GL_DEPTH_TEST);
    glDisable(GL_CULL_FACE);
    glfwSetMouseButtonCallback(window, (windowHandle, button, action, mods) -> {
      // button: Which button (e.g., GLFW_MOUSE_BUTTON_LEFT)
      // action: What happened (GLFW_PRESS or GLFW_RELEASE)

      if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
        // Example: Pass the action (1 for PRESS) to your HUD
        this.mouseAction = 1;
      } else {
        this.mouseAction = 0;
      }

    });
  }

  public void loop() {
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

    float lastTime = (float) glfwGetTime();

    while (!glfwWindowShouldClose(window)) {
      float currentTime = (float) glfwGetTime();
      float delta = currentTime - lastTime;
      lastTime = currentTime;

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      ClassConst.game.input();
      ClassConst.game.update(delta);
      ClassConst.game.render();

      glfwSwapBuffers(window);
      glfwPollEvents();
    }
  }

  public void cleanup() {
    glfwDestroyWindow(window);
    glfwTerminate();
  }

  public boolean isKeyPressed(int keyCode) {
    return glfwGetKey(window, keyCode) == GLFW_PRESS;
  }

  public int getWidth() {
    IntBuffer w = BufferUtils.createIntBuffer(1);
    IntBuffer h = BufferUtils.createIntBuffer(1);
    glfwGetFramebufferSize(window, w, h);
    return w.get(0);
  }

  public int getHeight() {
    IntBuffer w = BufferUtils.createIntBuffer(1);
    IntBuffer h = BufferUtils.createIntBuffer(1);
    glfwGetFramebufferSize(window, w, h);
    return h.get(0);
  }

  public float getMouseX() {
    double[] x = new double[1];
    double[] y = new double[1];
    glfwGetCursorPos(window, x, y);
    return (float) x[0];
  }

  public float getMouseY() {
    double[] x = new double[1];
    double[] y = new double[1];
    glfwGetCursorPos(window, x, y);
    return (float) y[0];
  }

  public int getMouseAction() {
    return mouseAction;
  }

  public void setMouseAction(int i) {
    this.mouseAction = i;
  }
}
