package ic.unicamp.splm.cli.cmd.graph.map;

import picocli.CommandLine;

@CommandLine.Command(name = "save-map")
public class SaveMap implements Runnable {
  public static final String command_name = "save-map";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
