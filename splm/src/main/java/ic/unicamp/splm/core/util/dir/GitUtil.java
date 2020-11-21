package ic.unicamp.splm.core.util.dir;


public class GitUtil {

    public static String retrieve_name(String original) {
        int index = original.lastIndexOf("/");
        return original.substring(index);
    }

}
