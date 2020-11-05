package ic.unicamp.splm.cli.cmd.graph.conf;

import picocli.CommandLine;

@CommandLine.Command(name = "show-conf")
public class ShowConf implements Runnable {
  public static final String command_name = "show-conf";

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
