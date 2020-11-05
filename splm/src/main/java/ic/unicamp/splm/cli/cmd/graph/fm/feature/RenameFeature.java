package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import picocli.CommandLine;

@CommandLine.Command(name = "rename-feature")
public class RenameFeature implements Runnable {
  public static final String command_name = "rename-feature";

  @CommandLine.Parameters(paramLabel = "name", description = "name of the feature")
  String name;

  @CommandLine.Parameters(paramLabel = "by", description = "name of the feature")
  String by;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.renameFeature(name, by);*/
  }
}
