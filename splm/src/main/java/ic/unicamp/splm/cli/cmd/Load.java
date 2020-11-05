package ic.unicamp.splm.cli.cmd;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "load")
public class Load implements Runnable {
  public static final String command_name = "load";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.loadData();
  }
}
