package ic.unicamp.splm.cli.cmd.graph.br;

import picocli.CommandLine;

@CommandLine.Command(name = "load-br")
public class LoadBr implements Runnable {
  public static final String command_name = "load-br";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
