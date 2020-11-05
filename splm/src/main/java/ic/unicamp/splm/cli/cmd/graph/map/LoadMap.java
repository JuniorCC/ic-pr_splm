package ic.unicamp.splm.cli.cmd.graph.map;

import picocli.CommandLine;

@CommandLine.Command(name = "load-map")
public class LoadMap implements Runnable {
  public static final String command_name = "load-map";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
