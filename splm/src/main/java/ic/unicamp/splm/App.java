package ic.unicamp.splm;

import ic.unicamp.splm.cli.Cmd;
import picocli.CommandLine;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    new CommandLine(new Cmd()).execute(args);
  }
}
