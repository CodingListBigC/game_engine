package org.bigacl.renderEngine.gui.menu.design;

import org.bigacl.renderEngine.gui.text.TextItems;
import org.bigacl.renderEngine.utils.consts.ClassConst;
import org.bigacl.renderEngine.utils.consts.Const;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class BoxItems {
  private final ArrayList<TextItems> items = new ArrayList<>();
  private final Vector3f textColor;
  private final Vector4f backgroundColor;
  private float padding = Const.PADDING;

  public BoxItems(Vector3f textColor, Vector4f backgroundColor) {
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
  }

  public BoxItems(Vector3f textColor, Vector4f backgroundColor, float padding) {
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    this.padding = padding;
  }

  /**
   * Renders the background box and all list items inside it.
   */
  public void renderAll(Vector2f position) {
    // 1. Calculate the total size needed based on all items in the list
    Vector2f textSize = calculateTotalSize();

    // Add padding to the total dimensions
    Vector2f boxSize = new Vector2f();
    boxSize.x = (padding * 2) + textSize.x;
    boxSize.y = (padding * 2) + textSize.y;

    // 2. Draw the Background FIRST (Painter's Algorithm: bottom layer)
    ClassConst.nanoVGUI.drawRect(position, boxSize, backgroundColor);

    // 3. Draw the Text SECOND (Painter's Algorithm: top layer)
    renderItems(position);
  }

  /**
   * Calculates the width and height of the combined text list.
   */
  private Vector2f calculateTotalSize() {
    Vector2f totalSize = new Vector2f(0, 0);
    for (TextItems item : items) {
      Vector2f itemSize = item.getDisplay().getSize();

      // Width is the width of the widest item
      if (itemSize.x > totalSize.x) {
        totalSize.x = itemSize.x;
      }

      // Height is the sum of all item heights plus spacing
      totalSize.y += itemSize.y + padding;
    }
    return totalSize;
  }

  /**
   * Loops through items and renders them vertically.
   */
  private void renderItems(Vector2f position) {
    // Start drawing text with the initial padding offset
    float currentY = position.y + (padding * 2);
    float startX = position.x + padding;

    for (TextItems item : items) {
      // Render this specific item at the current vertical offset
      item.getDisplay().render(new Vector2f(startX, currentY), this.textColor);

      // Move the "cursor" down for the next item based on its height + padding
      float itemHeight = item.getDisplay().getSize().y;

      // If this is 0, the list won't form. Let's add a fallback height of 20
      if (itemHeight <= 0) itemHeight = 20.0f;

      currentY += ClassConst.fontSizing.getStandardHeight(ClassConst.nanoVGUI.getVg(), item.getType()) + padding;
    }
  }

  public void addItem(TextItems textItems) {
    items.add(textItems);
  }

  public void updateInfo(int index, String info) {
    if (index >= 0 && index < items.size()) {
      items.get(index).updateInfo(info);
    }
  }
}