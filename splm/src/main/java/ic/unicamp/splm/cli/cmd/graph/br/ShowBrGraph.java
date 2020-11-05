package ic.unicamp.splm.cli.cmd.graph.br;

import picocli.CommandLine;

@CommandLine.Command(name = "show-br-graph")
public class ShowBrGraph implements Runnable {
  public static final String command_name = "show-br-graph";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
