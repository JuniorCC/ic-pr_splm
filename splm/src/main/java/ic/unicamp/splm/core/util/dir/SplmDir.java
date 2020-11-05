package ic.unicamp.splm.core.util.dir;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.SPLM_DIR;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.*;

public class SplmDir {

  public static Path get_splm_dir__as_path() {
    String currentDirectory = System.getProperty("user.dir");
    return Paths.get(currentDirectory, SPLM_DIR);
  }

  public static File get_splm_dir__as_file() {
    return new File(String.valueOf(SplmDir.get_splm_dir__as_path()));
  }

  public static boolean exists_splm_dir() {
    return SplmDir.get_splm_dir__as_file().exists();
  }


  public static void create_splm_dir_with_msg() {
    if (SplmDir.create_splm_dir()) {
      SplMgrLogger.info(INF_0__SPLM_DIR_CREATED, true);
    } else {
      SplMgrLogger.warn(WARN_4__SPLM_DIR_NOT_CREATED, true);
    }
  }

  public static boolean create_splm_dir() {
    File xgit_dir_as_file = SplmDir.get_splm_dir__as_file();
    boolean file_was_created = xgit_dir_as_file.mkdir();
    if (file_was_created) {
      CommonDir.setHiddenAttrib(SplmDir.get_splm_dir__as_path());
      return true;
    }
    return false;
  }

  public static void remove_splm_dir_with_msg() {
    if (SplmDir.remove_splm_dir()) {
      SplMgrLogger.info(INF_0__SPLM_DIR_REMOVED, true);
    } else {
      SplMgrLogger.warn(WARN_4__SPLM_DIR_NOT_REMOVED, true);
    }
  }

  public static boolean remove_splm_dir() {
    if (SplmDir.exists_splm_dir()) {
      try {
/*        Files.walk(SplmDir.get_splm_dir__as_path())
                .map(Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(File::delete);*/
        FileUtils.deleteDirectory(SplmDir.get_splm_dir__as_file());
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
