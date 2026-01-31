package org.bigacl.renderEngine.window;

import org.bigacl.renderEngine.logic.IGameLogic;
import org.bigacl.renderEngine.utils.consts.Const;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class WindowMaster {
  long window;

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
  }
  public void loop(IGameLogic gameLogic) {
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

    float lastTime = (float) glfwGetTime();

    while (!glfwWindowShouldClose(window)) {
      float currentTime = (float) glfwGetTime();
      float delta = currentTime - lastTime;
      lastTime = currentTime;

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      gameLogic.input();
      gameLogic.update(delta);
      gameLogic.render();

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
}
