package org.bigacl.renderEngine.gui.fields.button.tables;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static org.bigacl.renderEngine.utils.ColorSetter.setNewAlpha;
import static org.bigacl.renderEngine.utils.consts.ClassConst.nanoVGUI;

import java.awt.*;
import java.util.ArrayList;

public class TableMaster<listType extends InputInterface> {
  Vector2i rowColumns;
  Vector2f tableSize = new Vector2f(0);
  Vector2f location = new Vector2f(0);
  Vector2f itemSize = new Vector2f(0);
  Color background = setNewAlpha(Color.ORANGE, 200);

  ArrayList<listType> itemArray = new ArrayList<listType>();

  boolean newPos = false;

  public void addItem(listType item) {
    itemArray.add(item);
  }

  public void setRowColumns(Vector2i rowColumns) {
    this.rowColumns = rowColumns;
    newPos = true;
  }

  public void setTableSize(Vector2f tableSize) {
    this.tableSize = tableSize;
    newPos = true;
  }

  public void setSizes() {
    if (tableSize == null || rowColumns == null) {
      return;
    }
    itemSize.x = tableSize.x / rowColumns.x;
    itemSize.y = tableSize.y / rowColumns.y;
    for (int i = 0; i < itemArray.size(); i++) {
      itemArray.get(i).setSize(itemSize);
      itemArray.get(i).setLocation(getLocation(i));
    }
  }

  private Vector2f getLocation(int slot) {
    int column = (int) Math.floor((double) slot / rowColumns.x);
    int row = slot - (rowColumns.x * column);
    float xPos = row * this.itemSize.x;
    float yPos = column * this.itemSize.y;
    return new Vector2f(xPos, yPos).add(this.location);
  }

  public ArrayList<listType> getItemArray() {
    return itemArray;
  }

  public void setTableLocation(Vector2f location) {
    this.location = location;
    newPos = true;
  }

  public void render() {
    nanoVGUI.drawRect(this.location, this.tableSize, this.background);
    if (itemArray == null) {
      return;
    }
    if (newPos) {
      setSizes();
    }
    for (listType item : itemArray) {
      item.render();
    }
  }
}
