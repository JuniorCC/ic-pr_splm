package ic.unicamp.splm.cli.cmd.graph.fm;

import picocli.CommandLine;

@CommandLine.Command(name = "load-fm")
public class LoadFM implements Runnable {
  public static final String command_name = "load-fm";

  @CommandLine.Option(names = "-objects", description = "'load-feature -objects'")
  private boolean objects;

  @Override
  public void run() {
    if (objects) {
      loadFMFromObjects();
    } else {
      loadFMFromStage();
    }
  }

  private void loadFMFromObjects() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.loadFMfromObjects();*/
  }

  private void loadFMFromStage() {
    /*    SplMgr SplMgr = SplMgrBuilder.getSingletonInstance();
    SplMgr.loadFMfromStage();*/
  }
}
