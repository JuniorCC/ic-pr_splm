package ic.unicamp.splm.cli.cmd.spl;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "generate-branches")
public class GenerateBranches implements Runnable {
  public static final String command_name = "generate-branches";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
  }
}
