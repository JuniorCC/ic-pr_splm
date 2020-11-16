package ic.unicamp.splm.cli.cmd.graph.mp;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "show-mpm")
public class ShowMpM implements Runnable {
    public static final String command_name = "show-mpm";

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        splMgr.showMpM();
    }
}
