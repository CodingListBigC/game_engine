package org.bigacl.renderEngine.gui.fields.button.tables;

import org.bigacl.renderEngine.gui.fields.InputInterface;
import org.bigacl.renderEngine.gui.fields.button.addSubtract.AddSubtractButtonWithText;
import org.bigacl.renderEngine.utils.number.NumberLimits;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import static org.bigacl.renderEngine.utils.ColorSetter.setNewAlpha;
import static org.bigacl.renderEngine.utils.consts.ClassConst.nanoVGUI;
import static org.bigacl.renderEngine.utils.consts.ClassConst.window;

import java.awt.*;
import java.util.ArrayList;

public class TableMaster<listType extends InputInterface> {
  Vector2f wholeItemSize;
  Vector2i rowColumns;
  Vector2f tableSize = new Vector2f(0);
  Vector2f location = new Vector2f(0);
  Vector2f itemSize = new Vector2f(0);
  Color background = setNewAlpha(Color.ORANGE, 200);

  protected ArrayList<listType> itemArray = new ArrayList<listType>();

  boolean newPos = false;

  int amountPerPage = 0;
  int amountOfPages = 0;
  protected NumberLimits currentPage = new NumberLimits(1, 1);

  // Page Button Variable
  float pageButtonHeight = 50;
  AddSubtractButtonWithText pageButton = new AddSubtractButtonWithText("", new Vector2f(0), new Vector2f(0), 20, 20);

  public void addItem(listType item) {
    itemArray.add(item);
    newPos = true;
  }

  public void setRowColumns(Vector2i rowColumns) {
    this.rowColumns = rowColumns;
    newPos = true;
  }

  public void setWholeItemSize(Vector2f wholeItemSize) {
    this.wholeItemSize = wholeItemSize;
    this.tableSize = new Vector2f(this.wholeItemSize).sub(0, pageButtonHeight);
    this.newPos = true;
  }

  public void setTableSize(Vector2f tableSize) {
    this.tableSize = tableSize;
    newPos = true;
  }

  public void setSizes() {
    setPageButtonSize();
    setTableSizes();
  }

  private void setTableSizes() {
    if (tableSize == null || rowColumns == null) {
      return;
    }
    itemSize.x = tableSize.x / rowColumns.x;
    itemSize.y = tableSize.y / rowColumns.y;
    for (int i = 0; i < itemArray.size(); i++) {
      int itemPage = getPage(i);
      itemArray.get(i).setSize(itemSize);
      itemArray.get(i).setLocation(getLocation(i, itemPage));
    }
  }

  private void setPageButtonSize() {
    if (rowColumns == null || this.pageButton == null) {
      return;
    }
    this.amountPerPage = rowColumns.x * rowColumns.y;
    this.amountOfPages = (this.itemArray.size() + this.amountPerPage - 1) / this.amountPerPage;
    ;
    this.currentPage.setLimits(1, this.amountOfPages);
    this.pageButton.setVisible(true);
    this.pageButton.setItemPosition(new Vector2f(location).add(0, tableSize.y));
    this.pageButton.setItemSize(new Vector2f(wholeItemSize.x, this.pageButtonHeight));
  }

  private Vector2f getLocation(int slot, int page) {
    if (page == 1) {
      System.out.println("Test");
    }
    int column = (int) Math.floor((double) slot / rowColumns.y);
    int row = (slot - (rowColumns.y * column));
    float xPos = row * this.itemSize.x;
    float yPos = column * this.itemSize.y;
    return new Vector2f(xPos, yPos).add(this.location).sub(0, (this.tableSize.y) * page);
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
    renderPage();

    this.pageButton.setTextLabel(this.currentPage.getValue() + "/" + this.amountOfPages);
    this.pageButton.render();
  }

  private void renderPage() {
    for (listType item : itemArray) {
      int itemPage = getPage(itemArray.indexOf(item)) + 1;
      if (itemPage == this.currentPage.getValue()) {
        item.render();
      }
    }
  }

  public void checkClickDefault(Vector2d mouseLocation) {
    this.currentPage.add(this.pageButton.getInfo(mouseLocation));
  }

  protected int getPage(int index) {
    if (this.amountPerPage == 0) return 0;
    int page = (int) Math.floor(index / this.amountPerPage);

    return page;
  }
}
