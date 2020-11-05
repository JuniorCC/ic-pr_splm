package ic.unicamp.splm.cli.cmd.graph.conf;

import picocli.CommandLine;

@CommandLine.Command(name = "save-conf")
public class SaveConf implements Runnable {
  public static final String command_name = "save-conf";

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
