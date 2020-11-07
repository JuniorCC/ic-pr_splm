package ic.unicamp.splm.cli.cmd;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "save")
public class Save implements Runnable {
  public static final String command_name = "save";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.saveData();
  }
}
