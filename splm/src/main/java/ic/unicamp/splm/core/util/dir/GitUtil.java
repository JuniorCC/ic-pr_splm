package ic.unicamp.splm.core.util.dir;


import org.jetbrains.annotations.NotNull;

import static ic.unicamp.splm.core.util.id.IDTags.Merge_Tag;

public class GitUtil {
    @NotNull
    private static String filterName(String name) {
        return name.replace(" ", "_");
    }

    public static String retrieve_name(String original) {
        int index = original.lastIndexOf("/");
        return original.substring(index + 1);
    }

    public static String create_merge_tag(String name) {

        String filter = filterName(name);
        return String.format("%s%s", Merge_Tag, filter);
    }

    public static String retrieve_git_ignore_content() {

        String msg = "# develop\n" +
                ".idea/\n" +
                "pom.xml\n" +
                "splm.iml\n" +
                "src/\n" +
                "target/\n" +
                "\n" +
                "# prod\n" +
                ".splm.logs/\n" +
                ".gitignore\n";
        return String.format("%s%s", Merge_Tag, msg);
    }

    public static String create_local_branch_name(String branch_name) {
        return "refs/heads/"+branch_name;
    }
}
