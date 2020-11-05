package ic.unicamp.splm.cli.cmd.graph.conf;

import picocli.CommandLine;

@CommandLine.Command(name = "remove-prod-conf")
public class RemoveProdConf implements Runnable {
  public static final String command_name = "remove-prod-conf";

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
