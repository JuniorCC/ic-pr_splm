package ic.unicamp.splm.core.util.id;

import static ic.unicamp.splm.core.util.id.IDTags.*;

public class IDGenerator {

  public static String generateFeatureID(String name) {
    return String.format("%s%s", Feature_Tag, name);
  }

  public static String generateBranchID(String name) {
    return String.format("%s%s", Branch_Tag, name);
  }

  public static String generateVBranchID(String name) {
    return String.format("%s%s", VBranch_Tag, name);
  }

  public static String generateProductID(String name) {
    return String.format("%s%s", Product_Tag, name);
  }

  public static String generateMappingID(String name) {
    return String.format("%s%s", Mapping_Tag, name);
  }
}
