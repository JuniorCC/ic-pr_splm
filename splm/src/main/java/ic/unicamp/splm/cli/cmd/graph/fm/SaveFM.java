package ic.unicamp.splm.cli.cmd.graph.fm;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "save-fm")
public class SaveFM implements Runnable {
  public static final String command_name = "save-fm";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.saveData();
  }
}
