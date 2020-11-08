package ic.unicamp.splm.cli.cmd.basic;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__ENDING_FROM_PROMPT;

@CommandLine.Command(name = "exit")
public class Exit implements Runnable {
  public static final String command_name = "exit";

  @Override
  public void run() {
    SplMgrLogger.info(INF_0__ENDING_FROM_PROMPT, true);
  }
}
