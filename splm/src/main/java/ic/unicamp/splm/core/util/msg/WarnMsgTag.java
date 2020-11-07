package ic.unicamp.splm.core.util.msg;

public interface WarnMsgTag {
  String WARN_0__FEATURE_ROOT_ALREADY_EXITS = "Root Feature '%s' already exits in our DB";
  String WARN_1__FEATURE_PARENT_DOES_NOT_EXITS = "Parent Feature '%s' does not exits in our DB";
  String WARN_2__FEATURE_ALREADY_EXITS = "Feature '%s; already exits in our DB";

  String WARN_3__OBJ_DIR_NOT_CREATED = "Object Directory not created";
  String WARN_3__OBJ_DIR_ALREADY_EXITS = "Object Directory already exits";

  String WARN_4__OBJ_DIR_HASH_MAP_FILE_NOT_EXITS = "(Data) Hash Map File does not exits";
  String WARN_4__OBJ_DIR_HASH_MAP_FILE_NOT_CREATED = "(Data) Hash Map File not created";
  String WARN_5__OBJ_DIR_HASH_MAP_FILE_ALREADY_EXITS = "(Data) Hash Map File already exits";

  String WARN_4__OBJ_DIR_GRAPH_FILE_NOT_EXITS = "(Relations) Graph File does not exits";
  String WARN_4__OBJ_DIR_GRAPH_FILE_NOT_CREATED = "(Relations) Graph File not created";
  String WARN_5__OBJ_DIR_GRAPH_FILE_ALREADY_EXITS = "(Relations) Graph File file already exits";

  String WARN_3__WE_COULD_NOT_CREATE_ROOT_FEATURE = "we couldn't create ROOT Feature";

  String WARN_3__WE_COULD_NOT_CREATE_SPLM_DIR_BECAUSE_ALREADY_EXITS =
      "We could not create a new splm directory because there is already a .splm directory";
  String WARN_3__WE_COULD_NOT_CREATE_GIT_DIR_BECAUSE_ALREADY_EXITS =
      "We could not create a new git directory because there is already a .git directory";

  String WARN_4__SPLM_DIR_NOT_CREATED = "splm directory not created";
  String WARN_4__SPLM_DIR_NOT_REMOVED = "splm directory not removed";

  String WARN_4__GIT_DIR_NOT_CREATED = "git directory not created";
  String WARN_4__GIT_DIR_NOT_REMOVED = "git directory not removed";
}
