package ic.unicamp.splm.cli.cmd.dir;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "remove-dir",
    description = "The 'remove-dir' command removes the splm directory.")
public class RemoveDir implements Runnable {
  public static final String command_name = "remove-dir";

  @CommandLine.Option(
      names = "-git",
      description = "The 'remove-dir -all' command removes the .git directory.")
  private boolean git;

  @CommandLine.Option(
      names = "-all",
      description = "The 'remove-dir -all' command removes the object and stage directory.")
  private boolean all;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    if (git) {
      splMgr.removeGitDir();
      return;
    }
    if (all) {
      splMgr.removeAllDirs();
      return;
    }
    splMgr.removeSplmDir();
  }
}
