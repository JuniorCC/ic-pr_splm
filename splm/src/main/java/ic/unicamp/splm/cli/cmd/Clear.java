package ic.unicamp.splm.cli.cmd;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@CommandLine.Command(name = "clear")
public class Clear implements Runnable {
  public static final String command_name = "clear";
  private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS");

  @Override
  public void run() {
    try {
      /*   final String os = System.getProperty("os.name");
      if (os.contains("Windows")) {
        SplMgrLogger.info("Cleaning Windows console", true);
        Process process = Runtime.getRuntime().exec("cls");
        process.waitFor();
        SplMgrLogger.info("<-- terminal inner process -->");
        print(process.getInputStream(), "");
        print(process.getErrorStream(), "Error: ");
        SplMgrLogger.info("<-- end -->");

      } else {
        SplMgrLogger.info("Cleaning Linux console", true);
        Process process = Runtime.getRuntime().exec("clear");
        process.waitFor();
        SplMgrLogger.info("<-- terminal inner process -->");
        print(process.getInputStream(), "");
        print(process.getErrorStream(), "Error: ");
        SplMgrLogger.info("<-- end -->");
      }*/
    } catch (final Exception e) {
      SplMgrLogger.error(e.getMessage(), true);
    }
  }

  private void print(InputStream inputStream, String prefix) {

    Scanner scanner = new Scanner(inputStream, "UTF-8");
    while (scanner.hasNextLine()) {
      synchronized (this) {
        log(prefix + scanner.nextLine());
      }
    }
    scanner.close();

    /*        new Thread(() -> {
        log("<-- inner process -->");
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        while (scanner.hasNextLine()) {
            synchronized (this) {
                log(prefix + scanner.nextLine());
            }
        }
        scanner.close();
        log("<-- end -->");
    }
    ).start();*/
  }

  private synchronized void log(String message) {

    // SplMgrLogger.info(format.format(new Date()) + ": " + message);
    SplMgrLogger.info(message, true);
  }
}
