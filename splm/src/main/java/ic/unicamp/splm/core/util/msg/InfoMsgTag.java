package ic.unicamp.splm.core.util.msg;

public interface InfoMsgTag {
    String INF_0__ENDING_FROM_PROMPT = "Ending prompt";

    String INF_0__SPLM_ART_ASCII = " SPL Mgr";
    String INF_0__SPLM_VERSION = "SPLM Version: 0.1";
    String INF_0__SPLM_AUTHOR = "Author: Junior Cupe Casquina";
    String INF_0__SPLM_CONTACT = "Contact: jcupe.cas@gmail.com";
    String INF_0__WELCOME_SPLM = "<< SPL Manager -- Console APP >>";
    String INF_0__PROMPT = "(xgit) ";
    String INF_0__SCANNING_CORE_FILES = " ... inner process --> scanning splm core files ... ";
    String INF_0__END_SCANNING_CORE_FILES = " ...        end        ... ";

    String INF_0__SCANNING_VC_DIRECTORIES = " ... inner process --> scanning directories ... ";
    String INF_0__END_SCANNING_VC_DIRECTORIES = " ...        end        ... ";
    String INF_0__END_INNER_PROCESS= " ... ";
    String INF_0__CMD_ACCEPTED = "<cmd accepted>";
    String INF_0__CMD_END = "<end>";
    String INF_0__CMD_NOT_VALID = "<cmd not valid>";

    String INF_0__OBJ_DIR_CREATED = "object directory created";
    String INF_0__OBJ_DIR_HASH_MAP_FILE_CREATED = "Data (part 1) file created - hash map";
    String INF_0__OBJ_DIR_GRAPH_FILE_CREATED = "Data (part 2) file created - graph";

    String INF_0__SPLM_DIR_CREATED = "splm directory created";
    String INF_0__SPLM_DIR_REMOVED = "splm directory removed";

    String INF_0__GIT_DIR_CREATED = "git directory created";
    String INF_0__GIT_DIR_REMOVED = "git directory removed";
    String INFO_3__GIT_AND_SLPM_DIRECTORIES_REMOVED = "git and splm directories removed";

    String INFO_3__SPLM_DIR_DETECTED = "splm directory detected";
    String INFO_3__SPLM_DIR_NOT_DETECTED = "splm directory not detected";
    String INFO_3__GIT_DIR_DETECTED = "git directory detected";
    String INFO_3__GIT_DIR_NOT_DETECTED = "git directory not detected";

    String INFO_3__ADDED_ROOT_FEATURE = "Added '%s' root feature";
    String INFO_3__ADDED_FEATURE = "Added '%s' Feature";

    String INFO_3__LOADED_HASHMAP = "Loaded hashmap file";
    String INFO_3__LOADED_GRAPH = "Loaded graph file";
    String INF_0__MASTER_BRANCH_CREATED = "'master' git branch created";
    String INFO_0__ADDED_PRODUCT_RELATION_TO_FEATURE = "Added product relation to '%s' feature";
    String INFO_0__FEATURE_FOUND = "Feature '%s' found";
    String INFO_0__PRODUCT_ADDED = "Product '%s' added";

    String INF_0__CREATED_BRANCH_FROM = " Created git branch '%s' from '%S'";

    String INF_0__YOU_COMMIT_GENERATE_CONFLICTS_WITH_PRODUCT = "Your branch '%s' generate conflicts with the branch '%s' when producing " +
            "product '%s' \n : '%S'";


    String INFO_3__VC_DIR_DETECTED = "'%s' directory detected";
    String INFO_0__VC_DIRECTORY_NOT_DETECTED = "'%s' directory not detected";

    String INFO_3__HASHMAP_DETECTED = "Hashmap File detected (Data)";
    String INFO_3__GRAPH_DETECTED = "Graph File detected (Relations)";

    String INFO_3__HASHMAP_NOT_DETECTED = "Hashmap File not detected (Data)";
    String INFO_3__GRAPH_NOT_DETECTED = "Graph File not detected (Relations)";
}
