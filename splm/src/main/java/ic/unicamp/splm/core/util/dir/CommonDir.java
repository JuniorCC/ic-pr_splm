package ic.unicamp.splm.core.util.dir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CommonDir {
    public static void setHiddenAttrib(Path filePath) {
        try {
            Files.setAttribute(filePath, "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
