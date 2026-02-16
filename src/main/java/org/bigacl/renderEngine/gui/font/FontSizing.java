package org.bigacl.renderEngine.gui.font;

public class FontSizing {
  public final int HEADING_1_SIZE;
  public final int HEADING_2_SIZE;
  public final int HEADING_3_SIZE;
  public final int HEADING_4_SIZE;

  public final int NORMAL_TEXT_SIZE;

  public FontSizing(int NORMAL_TEXT_SIZE) {
    this.NORMAL_TEXT_SIZE = NORMAL_TEXT_SIZE;
    this.HEADING_4_SIZE = (int) (this.NORMAL_TEXT_SIZE * 1.125);
    this.HEADING_3_SIZE = (int) (this.HEADING_4_SIZE * 1.125);
    this.HEADING_2_SIZE = (int) (this.HEADING_3_SIZE * 1.125);
    this.HEADING_1_SIZE = (int) (this.HEADING_2_SIZE * 1.125);
  }

}