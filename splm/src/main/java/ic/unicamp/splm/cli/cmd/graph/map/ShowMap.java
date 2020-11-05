package ic.unicamp.splm.cli.cmd.graph.map;

import picocli.CommandLine;

@CommandLine.Command(name = "show-map")
public class ShowMap implements Runnable {
  public static final String command_name = "show-map";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showMapGraph();*/
  }
}
