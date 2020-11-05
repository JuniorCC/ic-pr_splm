package ic.unicamp.splm.cli.cmd.graph.map;

import picocli.CommandLine;

@CommandLine.Command(name = "show-map-graph")
public class ShowMapGraph implements Runnable {
  public static final String command_name = "show-map-graph";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showMapGraph();*/
  }
}
