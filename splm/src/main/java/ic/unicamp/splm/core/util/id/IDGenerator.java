package ic.unicamp.splm.core.util.id;

import org.jetbrains.annotations.NotNull;

import static ic.unicamp.splm.core.util.id.IDTags.*;

public class IDGenerator {

  public static String generateFeatureID(String name) {
    String filter = filterName(name);
    return String.format("%s%s", Feature_Tag, filter);
  }

  @NotNull
  private static String filterName(String name) {
    return name.replace(" ","_");
  }

  public static String generateBranchID(String name) {
    String new_name = filterName(name);
    return String.format("%s%s", Branch_Tag, new_name);
  }

  public static String generateVBranchID(String name) {
    String new_name = filterName(name);
    return String.format("%s%s", VBranch_Tag, new_name);
  }

  public static String generateProductID(String name) {
    String new_name = filterName(name);
    return String.format("%s%s", Product_Tag, new_name);
  }

  public static String generateMappingID(String name) {
    String new_name = filterName(name);
    return String.format("%s%s", Mapping_Tag, new_name);
  }
}
