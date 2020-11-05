package ic.unicamp.splm.cli.cmd.graph.fm.feature;

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
    /* SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    if (optional) {
      SplMgr.addOptionalFeature(name, parent, abs);
      return;
    }
    if (mandatory) {
      SplMgr.addMandatoryFeature(name, parent, abs);
      return;
    }
    if (or) {
      SplMgr.addOrFeature(name, parent, abs);
      return;
    }
    if (alternative) {
      SplMgr.addAlternativeFeature(name, parent, abs);
      return;
    }
    // default
    SplMgr.addMandatoryFeature(name, parent, abs);*/
  }
}
