package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import picocli.CommandLine;

@CommandLine.Command(name = "move-feature")
public class MoveFeature implements Runnable {
  public static final String command_name = "move-feature";

  @CommandLine.Parameters(paramLabel = "original", description = "name of the feature")
  String original;

  @CommandLine.Parameters(paramLabel = "to", description = "name of the feature")
  String to;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.moveFeatureFromTo(original, to);*/
  }
}
