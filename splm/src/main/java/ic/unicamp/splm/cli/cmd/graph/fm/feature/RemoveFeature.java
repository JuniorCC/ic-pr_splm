package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import picocli.CommandLine;

@CommandLine.Command(name = "remove-feature")
public class RemoveFeature implements Runnable {
  public static final String command_name = "remove-feature";

  @CommandLine.Parameters(paramLabel = "name", description = "name of the feature")
  String name;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.removeFeature(name);*/
  }
}
