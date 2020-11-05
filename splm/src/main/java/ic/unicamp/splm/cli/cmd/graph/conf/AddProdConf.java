package ic.unicamp.splm.cli.cmd.graph.conf;

import picocli.CommandLine;

@CommandLine.Command(name = "add-prod-conf")
public class AddProdConf implements Runnable {
  public static final String command_name = "add-prod-conf";

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
