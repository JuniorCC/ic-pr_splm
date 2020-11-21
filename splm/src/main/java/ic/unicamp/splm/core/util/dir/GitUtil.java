package ic.unicamp.splm.core.util.dir;


import org.jetbrains.annotations.NotNull;

import static ic.unicamp.splm.core.util.id.IDTags.Feature_Tag;
import static ic.unicamp.splm.core.util.id.IDTags.Merge_Tag;

public class GitUtil {
    @NotNull
    private static String filterName(String name) {
        return name.replace(" ", "_");
    }

    public static String retrieve_name(String original) {
        int index = original.lastIndexOf("/");
        return original.substring(index);
    }

    public static String create_merge_tag(String name) {

        String filter = filterName(name);
        return String.format("%s%s", Merge_Tag, filter);
    }

}
