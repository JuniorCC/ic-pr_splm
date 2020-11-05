package ic.unicamp.splm.core.util.dir;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.GIT_DIR;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__GIT_DIR_CREATED;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__GIT_DIR_REMOVED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_4__GIT_DIR_NOT_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_4__GIT_DIR_NOT_REMOVED;

public class GitDir {

  public static Path get_git_dir__as_path() {
    String currentDirectory = System.getProperty("user.dir");
    return Paths.get(currentDirectory, GIT_DIR);
  }

  public static File get_git_dir__as_file() {
    return new File(String.valueOf(GitDir.get_git_dir__as_path()));
  }

  public static boolean exists_git_dir() {
    return GitDir.get_git_dir__as_file().exists();
  }

  public static void create_git_dir_with_msg() {
    if (GitDir.create_git_dir()) {
      SplMgrLogger.info(INF_0__GIT_DIR_CREATED, true);
    } else {
      SplMgrLogger.warn(WARN_4__GIT_DIR_NOT_CREATED, true);
    }
  }

  public static boolean create_git_dir() {
    try {
      String currentDirectory = System.getProperty("user.dir");
      File currentDirectoryFile = new File(String.valueOf(currentDirectory));
      Git.init().setDirectory(currentDirectoryFile).call();
      return true;
    } catch (GitAPIException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static void remove_git_dir_with_msg() {
    if (GitDir.remove_git_dir()) {
      SplMgrLogger.info(INF_0__GIT_DIR_REMOVED, true);
    } else {
      SplMgrLogger.warn(WARN_4__GIT_DIR_NOT_REMOVED, true);
    }
  }

  public static boolean remove_git_dir() {
    if (GitDir.exists_git_dir()) {

      try {
        FileUtils.deleteDirectory(GitDir.get_git_dir__as_file());
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      return true;
    }
  }
}
