package ic.unicamp.splm.cli.cmd;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INFO_3__SPLM_DIR_DETECTED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_3__WE_COULD_NOT_CREATE_SPLM_DIR_BECAUSE_ALREADY_EXITS;

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
      splMgr.create_hard_all_dirs(); // hard means 'and remove'
      splMgr.initGit();
    } else {
      if (splMgr.exists_splm_dir()) {
        if (f) {
          splMgr.create_hard_splm_dir();
          splMgr.create_soft_git_dir(); // soft means it not exits
          splMgr.initGit();
        } else {
          SplMgrLogger.info(INFO_3__SPLM_DIR_DETECTED, true);
          SplMgrLogger.warn(WARN_3__WE_COULD_NOT_CREATE_SPLM_DIR_BECAUSE_ALREADY_EXITS, true);
        }
      } else {
        splMgr.create_splm_dir();
        splMgr.create_soft_git_dir();
        splMgr.initGit();
      }
    }

  }
}
