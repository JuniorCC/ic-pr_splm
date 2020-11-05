package ic.unicamp.splm.cli.cmd.dir;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "remove-dir",
    description = "The 'remove-dir' command removes the splm directory.")
public class RemoveDir implements Runnable {
  public static final String command_name = "remove-dir";

  @CommandLine.Option(
      names = "-git",
      description = "The 'remove-dir -all' command removes the .git directory.")
  private boolean git;

  @CommandLine.Option(
      names = "-all",
      description = "The 'remove-dir -all' command removes the object and stage directory.")
  private boolean all;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    if (git) {
      splMgr.remove_git_dir();
      return;
    }
    if (all) {
      splMgr.remove_all_dirs();
      return;
    }
    splMgr.remove_splm_dir();
  }

  /* private void __remove_xgit_directory() {
    if (Common.exists_xgit_dir()) {
      if (Common.remove_xgit_dir()) {
        String message = "Xgit directory removed";
        SplMgrLogger.info(message, true);
      } else {
        String message = "We could not remove the xgit directory";
        SplMgrLogger.warn(message, true);
      }
    } else {
      String message = "Xgit directory does not exist";
      SplMgrLogger.warn(message, true);
    }
  }

  private void __remove_stage_directory() {
    if (Common.exists_xgit_stage_dir()) {
      if (Common.remove_xgit_stage_dir()) {
        String message = "Xgit stage directory removed";
        SplMgrLogger.info(message, true);
      } else {
        String message = "We could not remove the xgit stage directory";
        SplMgrLogger.warn(message, true);
      }
    } else {
      String message = "Xgit stage directory does not exist";
      SplMgrLogger.warn(message, true);
    }
  }

  private void __remove_object_directory() {
    if (Common.exists_xgit_object_dir()) {
      if (Common.remove_xgit_object_dir()) {
        String message = "Xgit directory removed";
        SplMgrLogger.info(message, true);
      } else {
        String message = "We could not remove the xgit directory";
        SplMgrLogger.warn(message, true);
      }
    } else {
      String message = "Xgit object directory does not exist";
      SplMgrLogger.warn(message, true);
    }
  }

  private void __remove_all_directories() {
    if (Common.remove_all_directories()) {
      String message = "Xgit and Git directories removed";
      SplMgrLogger.info(message, true);
    } else {
      String message = "We could not remove the xgit and git directories";
      SplMgrLogger.warn(message, true);
    }
  }

  private void __remove_git_directory() {
    if (Common.exists_git_dir()) {
      if (Common.remove_git_dir()) {
        String message = "Git directory removed";
        SplMgrLogger.info(message, true);
      } else {
        String message = "We could not remove the .git directory";
        SplMgrLogger.warn(message, true);
      }
    } else {
      String message = "Git directory does not exist";
      SplMgrLogger.warn(message, true);
    }
  }*/
}
