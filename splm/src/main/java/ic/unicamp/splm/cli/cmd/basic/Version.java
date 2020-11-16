package ic.unicamp.splm.cli.cmd.basic;

import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__SPLM_VERSION;

@CommandLine.Command(name = "version")
public class Version implements Runnable {
    public static final String command_name = "version";

    @Override
    public void run() {
        SplMgrLogger.info(INF_0__SPLM_VERSION, true);
    }
}
