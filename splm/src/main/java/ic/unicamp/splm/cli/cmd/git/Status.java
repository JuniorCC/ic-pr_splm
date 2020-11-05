package ic.unicamp.splm.cli.cmd.git;

import picocli.CommandLine;

@CommandLine.Command(name = "status")
public class Status implements Runnable {
  public static final String command_name = "status";

  @Override
  public void run() {}
}
