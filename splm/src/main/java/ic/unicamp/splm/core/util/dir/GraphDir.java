package ic.unicamp.splm.core.util.dir;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ic.unicamp.splm.core.util.dir.DirTag.*;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__OBJ_DIR_GRAPH_FILE_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_4__OBJ_DIR_GRAPH_FILE_NOT_CREATED;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_5__OBJ_DIR_GRAPH_FILE_ALREADY_EXITS;

public class GraphDir {

    // xgit/object->hashMap
    public static Path get_splm_obj_graph_file__as_path() {
        String currentDirectory = System.getProperty("user.dir");
        return Paths.get(currentDirectory, SPLM_DIR, OBJECT_DIR, GRAPH_FILE);
    }

    public static File get_splm_obj_graph_file__as_file() {
        Path path = GraphDir.get_splm_obj_graph_file__as_path();
        return new File(String.valueOf(path));
    }

    public static boolean exists_splm_obj_graph_file() {
        return GraphDir.get_splm_obj_graph_file__as_file().exists();
    }

    public static void create_splm_obj_graph_file_with_msg() {
        if (GraphDir.create_splm_obj_graph_file()) {
            SplMgrLogger.info(INF_0__OBJ_DIR_GRAPH_FILE_CREATED, true);
        } else {
            SplMgrLogger.warn(WARN_4__OBJ_DIR_GRAPH_FILE_NOT_CREATED, true);
        }
    }

    public static boolean create_splm_obj_graph_file() {
        if (!ObjectDir.exists_splm_obj_dir()) {
            ObjectDir.create_splm_obj_dir_with_msg();
            return __create_obj_graph_file();
        } else {
            if (GraphDir.exists_splm_obj_graph_file()) {
                SplMgrLogger.warn(WARN_5__OBJ_DIR_GRAPH_FILE_ALREADY_EXITS, true);
                return false;
            }
            return __create_obj_graph_file();
        }
    }

    private static boolean __create_obj_graph_file() {
        File file = GraphDir.get_splm_obj_graph_file__as_file();
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
