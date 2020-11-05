package ic.unicamp.splm.core.util.dir;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.GIT_DIR;

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
