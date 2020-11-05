package ic.unicamp.splm.cli.cmd.dir;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "file-status",
    description =
        "The 'file-status' command shows the list of files used to store the state of the tool.")
public class FileStatus implements Runnable {
  public static final String command_name = "file-status";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
  }
}
