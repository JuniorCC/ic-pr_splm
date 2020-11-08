package ic.unicamp.splm.cli.cmd.graph;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "fulfill-graph")
public class FulfillGraph implements Runnable {
  public static final String command_name = "fulfill-graph";

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    splMgr.fulfillGraph();
  }
}
