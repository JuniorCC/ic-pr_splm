package ic.unicamp.splm.cli.cmd.graph.map;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "generate-map")
public class GenerateMap implements Runnable {
  public static final String command_name = "generate-map";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.generateMap();
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.GenerateMapGraph();
    SplMgr.showMapGraph();*/
  }
}
