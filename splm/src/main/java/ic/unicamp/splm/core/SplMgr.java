package ic.unicamp.splm.core;

import ic.unicamp.splm.core.data.DataManager;
import ic.unicamp.splm.core.data.DataManagerBuilder;
import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.dir.SplmDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import ic.unicamp.splm.core.vc.git.GitMgr;
import ic.unicamp.splm.core.vc.git.GitMgrBuilder;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INFO_3__GIT_AND_SLPM_DIRECTORIES_REMOVED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_3__WE_COULD_NOT_CREATE_GIT_DIR_BECAUSE_ALREADY_EXITS;

public class SplMgr {
  DataManager dataManager;
  GitMgr gitMgr;

  public SplMgr() {
    dataManager = DataManagerBuilder.getSingletonInstance();
    gitMgr = GitMgrBuilder.getSingletonInstance();
  }

  public void loadData() {
    dataManager.loadData();
  }

  public void saveData() {
    dataManager.saveData();
  }

  public void remove_all_dirs() {
    if (SplmDir.exists_splm_dir()) {
      SplmDir.remove_splm_dir_with_msg();
    }
    if (GitDir.exists_git_dir()) {
      GitDir.remove_git_dir_with_msg();
    }
    SplMgrLogger.info(INFO_3__GIT_AND_SLPM_DIRECTORIES_REMOVED, true);
  }

  public void create_hard_all_dirs() {
    remove_all_dirs();
    SplmDir.create_splm_dir_with_msg();
    GitDir.create_git_dir_with_msg();
  }

  public void create_hard_splm_dir() {
    if (SplmDir.exists_splm_dir()) {
      SplmDir.remove_splm_dir_with_msg();
    }
    SplmDir.create_splm_dir_with_msg();
  }

  public void create_soft_git_dir() {
    if (!GitDir.exists_git_dir()) {
      GitDir.create_git_dir_with_msg();
    } else {
      SplMgrLogger.warn(WARN_3__WE_COULD_NOT_CREATE_GIT_DIR_BECAUSE_ALREADY_EXITS, true);
    }
  }

  public boolean exists_splm_dir() {
    return SplmDir.exists_splm_dir();
  }

  public void create_splm_dir() {
    SplmDir.create_splm_dir_with_msg();
  }

  public void initGit() {
    gitMgr.init();
  }

  public void remove_git_dir() {
    GitDir.remove_git_dir_with_msg();
  }

  public void remove_splm_dir() {
    SplmDir.remove_splm_dir_with_msg();
  }
}
