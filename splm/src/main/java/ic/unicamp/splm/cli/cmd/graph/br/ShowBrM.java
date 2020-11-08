package ic.unicamp.splm.cli.cmd.graph.br;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "show-brm")
public class ShowBrM implements Runnable {
  public static final String command_name = "show-brm";

  @CommandLine.Option(names = "-data", description = "")
  private boolean data;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    if (data) {
      splMgr.showRawBrM();
      return;
    }
    splMgr.showBrM();
  }
}
