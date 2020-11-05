package ic.unicamp.splm.cli.cmd.graph.fm;

import picocli.CommandLine;

@CommandLine.Command(name = "save-fm")
public class SaveFM implements Runnable {
  public static final String command_name = "save-fm";

  @CommandLine.Option(names = "-prod", description = "'save-fm -prod'")
  private boolean prod;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    if (prod) {
      SplMgr.saveFMinObjects();
    } else {
      SplMgr.saveFMinStage();
    }*/
  }
}
