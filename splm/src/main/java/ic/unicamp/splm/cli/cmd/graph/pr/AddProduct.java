package ic.unicamp.splm.cli.cmd.graph.pr;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "add-product")
public class AddProduct implements Runnable {
  public static final String command_name = "add-product";

  @CommandLine.Parameters(paramLabel = "name", description = "product name")
  String name;
  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
  }
}
