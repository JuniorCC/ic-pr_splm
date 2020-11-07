package ic.unicamp.splm.cli.cmd.graph.fm;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "init-fm")
public class InitFM implements Runnable {
  public static final String command_name = "init-fm";

  @CommandLine.Parameters(
      paramLabel = "name",
      description = "name of the feature",
      defaultValue = "Base")
  String name;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.initFM(name);
  }
}
