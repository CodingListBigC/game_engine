package org.bigacl.renderEngine.designItem;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class BuildItemData {
  // Matches JSON "name": { "main": "Stair", ... }
  public NameInfo name;

  public String unit;

  @SerializedName("amount_of_types")
  public int number_of_types;

  public String type;

  @SerializedName("modelTypes")
  public Map<String, ModelTypeDetails> modelTypes;

  public static class NameInfo {
    public String main;
    public String plural;
  }

  public static class ModelTypeDetails {
    public String partName;
    public int defaultFacing;
    public SeparateParts separateParts;
  }

  public static class SeparateParts {
    public String model;
    // Matches JSON "texture": { "0": "" }
    public Map<String, String> texture;
  }
}