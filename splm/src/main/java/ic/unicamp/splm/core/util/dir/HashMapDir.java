package ic.unicamp.splm.core.util.dir;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.*;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__OBJ_DIR_HASH_MAP_FILE_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_4__OBJ_DIR_HASH_MAP_FILE_NOT_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_5__OBJ_DIR_HASH_MAP_FILE_ALREADY_EXITS;

public class HashMapDir {

  // xgit/object->hashMap
  public static Path get_splm_obj_hash_map_file__as_path() {
    String currentDirectory = System.getProperty("user.dir");
    return Paths.get(currentDirectory, SPLM_DIR, OBJECT_DIR, HASH_MAP_FILE);
  }

  public static File get_splm_obj_hash_map_file__as_file() {
    Path path = HashMapDir.get_splm_obj_hash_map_file__as_path();
    return new File(String.valueOf(path));
  }

  public static boolean exists_splm_obj_hash_map_file() {
    return HashMapDir.get_splm_obj_hash_map_file__as_file().exists();
  }

  public static void create_splm_obj_hash_map_file_with_msg() {
    if (HashMapDir.create_splm_obj_hash_map_file()) {
      SplMgrLogger.info(INF_0__OBJ_DIR_HASH_MAP_FILE_CREATED, true);
    } else {
      SplMgrLogger.warn(WARN_4__OBJ_DIR_HASH_MAP_FILE_NOT_CREATED, true);
    }
  }

  public static boolean create_splm_obj_hash_map_file() {
    if (!ObjectDir.exists_splm_obj_dir()) {
      ObjectDir.create_splm_obj_dir_with_msg();
      return __create_obj_hash_map_file();
    } else {
      if (HashMapDir.exists_splm_obj_hash_map_file()) {
        SplMgrLogger.warn(WARN_5__OBJ_DIR_HASH_MAP_FILE_ALREADY_EXITS, true);
        return false;
      }
      return __create_obj_hash_map_file();
    }
  }

  private static boolean __create_obj_hash_map_file() {
    File file = HashMapDir.get_splm_obj_hash_map_file__as_file();
    try {
      return file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
