package ic.unicamp.splm.core.util.dir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CommonDir {
  public static void set_hidden_attrib(Path filePath) {
    try {
      Files.setAttribute(filePath, "dos:hidden", true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
