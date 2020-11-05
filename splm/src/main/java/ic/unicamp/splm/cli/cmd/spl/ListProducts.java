package ic.unicamp.splm.cli.cmd.spl;

import picocli.CommandLine;

@CommandLine.Command(name = "list-products")
public class ListProducts implements Runnable {
  public static final String command_name = "list-products";

  @Override
  public void run() {}
}
