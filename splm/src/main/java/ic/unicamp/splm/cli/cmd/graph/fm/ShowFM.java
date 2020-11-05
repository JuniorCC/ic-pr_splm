package ic.unicamp.splm.cli.cmd.graph.fm;

import picocli.CommandLine;

@CommandLine.Command(name = "show-fm")
public class ShowFM implements Runnable {
  public static final String command_name = "show-fm";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphical;

  @CommandLine.Option(names = "-full", description = "'init -graphical'")
  private boolean full;

  @Override
  public void run() {
    /*  SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    if (graphical) {
      SplMgr.showGraphicalFM();
      return;
    }
    if (full) {
      SplMgr.showFullFM();
    } else {
      SplMgr.showFM();
    }*/
  }
}
