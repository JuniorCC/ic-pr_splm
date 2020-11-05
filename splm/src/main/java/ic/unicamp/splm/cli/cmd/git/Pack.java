package ic.unicamp.splm.cli.cmd.git;

import picocli.CommandLine;

@CommandLine.Command(name = "pack")
public class Pack implements Runnable {
  public static final String command_name = "pack";

  @Override
  public void run() {}
}
