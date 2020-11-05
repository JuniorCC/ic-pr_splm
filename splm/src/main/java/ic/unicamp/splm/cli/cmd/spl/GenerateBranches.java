package ic.unicamp.splm.cli.cmd.spl;

import picocli.CommandLine;

@CommandLine.Command(name = "generate-branches")
public class GenerateBranches implements Runnable {
  public static final String command_name = "generate-branches";

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.generateGitBranches();*/
  }
}
