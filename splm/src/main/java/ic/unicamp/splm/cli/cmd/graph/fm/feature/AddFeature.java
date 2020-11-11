package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureMode;
import picocli.CommandLine;

@CommandLine.Command(name = "add-feature")
public class AddFeature implements Runnable {
  public static final String command_name = "add-feature";

  @CommandLine.Parameters(paramLabel = "parent", description = "parent feature")
  String parent;

  @CommandLine.Parameters(paramLabel = "name", description = "name of the feature")
  String name;

  @CommandLine.Option(names = "-optional", description = "'create-feature -optional'")
  private boolean optional;

  @CommandLine.Option(names = "-mandatory", description = "'create-feature -mandatory'")
  private boolean mandatory;

  @CommandLine.Option(names = "-or", description = "'create-feature -or'")
  private boolean or;

  @CommandLine.Option(names = "-alternative", description = "'create-feature -alternative'")
  private boolean alternative;

  @CommandLine.Option(names = "-a", description = "'create-feature -abstract'")
  private boolean abs;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    FeatureMode featureMode = FeatureMode.CONCRETE;

    if (abs) {
      featureMode = FeatureMode.ABSTRACT;
    }
    if (optional) {
      splMgr.add_optional_feature(parent, name, featureMode);
      return;
    }
    if (mandatory) {
      splMgr.add_mandatory_feature(parent, name, featureMode);
      return;
    }
    if (or) {
      splMgr.add_or_feature(parent, name, featureMode);
      return;
    }
    if (alternative) {
      splMgr.add_alternative_feature(parent, name, featureMode);
      return;
    }
    // default
    splMgr.add_mandatory_feature(parent, name, featureMode);
  }
}
