package ic.unicamp.splm.cli.cmd.graph.br;

import picocli.CommandLine;

@CommandLine.Command(name = "save-br")
public class SaveBr implements Runnable {
  public static final String command_name = "save-br";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
