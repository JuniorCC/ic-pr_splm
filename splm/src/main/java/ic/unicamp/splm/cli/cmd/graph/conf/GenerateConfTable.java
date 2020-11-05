package ic.unicamp.splm.cli.cmd.graph.conf;

import picocli.CommandLine;

@CommandLine.Command(name = "generate-conf-table")
public class GenerateConfTable implements Runnable {
  public static final String command_name = "generate-conf-table";

  @CommandLine.Option(names = "-graphical", description = "'init -graphical'")
  private boolean graphic;

  @Override
  public void run() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.showBranchGraph();*/
  }
}
