package org.bigacl.renderEngine.gui.fields.button.tables;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.ArrayList;

public class TableMaster<listType extends InputInterface> {
  Vector2i rowColumns;
  Vector2f tableSize;
  Vector2f itemSize;

  ArrayList<listType> itemArray;

  public void addItem(listType item) {
    itemArray.add(item);
  }

  public void setRowColumns(Vector2i rowColumns) {
    this.rowColumns = rowColumns;
  }

  public void setTableSize(Vector2f tableSize) {
    this.tableSize = tableSize;
  }

  public void setSizes() {
    itemSize.x = tableSize.x / rowColumns.x;
    itemSize.y = tableSize.y / rowColumns.y;
    for (int i = 0; i < itemArray.size(); i++) {
      itemArray.get(i).setSize(itemSize);
      itemArray.get(i).setLocation(getLocation(i));

    }
  }

  private Vector2f getLocation(int slot) {
    int row = (int) Math.floor((double) slot / rowColumns.y);
    int column = slot - (rowColumns.y * row);
    float xPos = row * this.itemSize.x;
    float yPos = column * this.itemSize.y;
    return new Vector2f(xPos, yPos);
  }
}
