package ic.unicamp.splm.cli.cmd.graph.fm;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "show-fm")
public class ShowFM implements Runnable {
  public static final String command_name = "show-fm";

  @CommandLine.Option(names = "-data", description = "")
  private boolean data;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    if (data) {
      splMgr.showRawFM();
      return;
    }
    splMgr.showFM();
  }
}
