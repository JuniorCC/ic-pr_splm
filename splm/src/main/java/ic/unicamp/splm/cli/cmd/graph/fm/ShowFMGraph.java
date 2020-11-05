package ic.unicamp.splm.cli.cmd.graph.fm;

import picocli.CommandLine;

@CommandLine.Command(name = "show-fm-graph")
public class ShowFMGraph implements Runnable {
  public static final String command_name = "show-fm-graph";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphical;

  @CommandLine.Option(names = "-complete", description = "'init -graphical'")
  private boolean complete;

  @Override
  public void run() {
    /*SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    if (graphical) {
      SplMgr.showGraphicalFM();
      return;
    }
    if (complete) {
      SplMgr.showFullFM();
    } else {
      SplMgr.showFM();
    }*/
  }
}
