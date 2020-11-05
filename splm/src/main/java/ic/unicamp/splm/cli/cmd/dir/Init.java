package ic.unicamp.splm.cli.cmd.dir;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "init",
    description = "This command will create the splm and git directories if they do not exist.")
public class Init implements Runnable {
  public static final String command_name = "init";

  @CommandLine.Option(
      names = "-f",
      description =
          "The 'init -f' command removes the previous splm directory firstly if it exists.")
  private boolean f;

  @CommandLine.Option(
      names = "-ff",
      description =
          "The 'init -ff' command removes the previous splm and git directory firstly if they do exists.")
  private boolean ff;

  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
     if (ff) {
      if (splMgr.__exists_splm_dir()) {
        splMgr.__remove_splm_dir();
      }
      if (splMgr.__exists_git_dir()) {
        splMgr.__remove_git_directory();
      }
       splMgr.__create_xgit_dir();
       splMgr.__create_git_dir();
    } else {
      if (splMgr.__exists_splm_dir()) {
        if (f) {
          splMgr.__remove_splm_dir();
          splMgr.__create_xgit_dir();
          splMgr.__create_git_directory_if_not_exists();
        } else {
          String message =
              "We could not create a new xgit directory because there is a .xgit directory in your path.";
          SplMgrLogger.warn(message, true);
        }
      } else {
        splMgr.__create_xgit_dir();
        splMgr.__create_git_directory_if_not_exists();
      }
    }
    //

    //SplMgr.initGit();
  }

 /*  private boolean __exists_splm_dir() {
    return Common.exists_xgit_dir();
  }

  private boolean __exists_git_dir() {
    return Common.exists_git_dir();
  }

  private void __create_git_directory_if_not_exists() {
    if (!Common.exists_git_dir()) {
      __create_git_dir();
    } else {
      String message =
          "We could not create a new Git directory because we detected a .git directory in your path";
      SplMgrLogger.warn(message, true);
    }
  }

  private void __create_xgit_dir() {
    if (Common.create_xgit_dir()) {
      String message = "Xgit directory created";
      SplMgrLogger.info(message, true);
    } else {
      String message = "We could not create the Xgit directory";
      SplMgrLogger.error(message, true);
    }
  }

  private void __remove_splm_dir() {
    if (Common.remove_xgit_dir()) {
      String message = "Xgit directory removed";
      SplMgrLogger.info(message, true);

    } else {
      String message = "We could not remove the Xgit directory";
      SplMgrLogger.error(message, true);
    }
  }

  private void __create_git_dir() {
    if (Common.create_git_dir()) {
      String message = "Git directory created";
      SplMgrLogger.info(message, true);
    } else {
      String message = "We could not create the Git directory";
      SplMgrLogger.error(message, true);
    }
  }

  private void __remove_git_directory() {
    if (Common.remove_git_dir()) {
      String message = "Git directory removed";
      SplMgrLogger.info(message, true);
    } else {
      String message = "We could not remove the Git directory";
      SplMgrLogger.error(message, true);
    }
  }*/
}
