package ic.unicamp.splm.core.util.dir;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.OBJECT_DIR;
import static ic.unicamp.splm.core.util.dir.DirTag.SPLM_DIR;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__OBJ_DIR_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_3__OBJ_DIR_ALREADY_EXITS;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_3__OBJ_DIR_NOT_CREATED;

public class ObjectDir {

  // splm/object
  public static Path get_splm_obj_dir__as_path() {
    String currentDirectory = System.getProperty("user.dir");
    return Paths.get(currentDirectory, SPLM_DIR, OBJECT_DIR);
  }

  public static File get_splm_obj_dir__as_file() {
    Path path = ObjectDir.get_splm_obj_dir__as_path();
    return new File(String.valueOf(path));
  }

  public static boolean exists_splm_obj_dir() {
    return ObjectDir.get_splm_obj_dir__as_file().exists();
  }

  public static void create_splm_obj_dir_with_msg() {
    if (ObjectDir.create_splm_obj_dir()) {
      SplMgrLogger.info(INF_0__OBJ_DIR_CREATED, true);
    } else {
      SplMgrLogger.warn(WARN_3__OBJ_DIR_NOT_CREATED, true);
    }
  }

  public static boolean create_splm_obj_dir() {
    if (ObjectDir.exists_splm_obj_dir()) {
      SplMgrLogger.warn(WARN_3__OBJ_DIR_ALREADY_EXITS, true);
      return false;
    }
    return create_obj_dir();
  }

  private static boolean create_obj_dir() {
    File object_dir_as_file = ObjectDir.get_splm_obj_dir__as_file();
    boolean file_was_created = object_dir_as_file.mkdir();
    if (file_was_created) {
      CommonDir.setHiddenAttrib(ObjectDir.get_splm_obj_dir__as_path());
      return true;
    }
    return false;
  }

  public static boolean remove_splm_obj_dir() {
    if (ObjectDir.exists_splm_obj_dir()) {
      try {
        Files.walk(ObjectDir.get_splm_obj_dir__as_path())
            .map(Path::toFile)
            .sorted((o1, o2) -> -o1.compareTo(o2))
            .forEach(File::delete);
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
