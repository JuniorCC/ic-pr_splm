package ic.unicamp.splm.cli.cmd.git;

import picocli.CommandLine;

@CommandLine.Command(name = "commit")
public class Commit implements Runnable {
  public static final String command_name = "commit";

  @Override
  public void run() {}
}
