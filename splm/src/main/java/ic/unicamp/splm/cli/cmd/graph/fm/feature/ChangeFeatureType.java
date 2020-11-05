package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import picocli.CommandLine;

@CommandLine.Command(name = "change-feature-type")
public class ChangeFeatureType implements Runnable {
  public static final String command_name = "change-feature-type";

  @CommandLine.Parameters(paramLabel = "name", description = "name of the feature")
  String name;

  @CommandLine.Option(names = "-optional", description = "'change-feature-type -optional'")
  private boolean optional;

  @CommandLine.Option(names = "-mandatory", description = "'change-feature-type -mandatory'")
  private boolean mandatory;

  @CommandLine.Option(names = "-or", description = "'change-feature-type -or'")
  private boolean or;

  @CommandLine.Option(names = "-alternative", description = "'change-feature-type -alternative'")
  private boolean alternative;

  @Override
  public void run() {
    /* SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    if (optional) {
      SplMgr.changeToOptional(name);
      return;
    }
    if (mandatory) {
      SplMgr.changeToMandatory(name);
      return;
    }
    if (or) {
      SplMgr.changeToOr(name);
      return;
    }
    if (alternative) {
      SplMgr.changeToAlternative(name);
    }*/
  }
}
