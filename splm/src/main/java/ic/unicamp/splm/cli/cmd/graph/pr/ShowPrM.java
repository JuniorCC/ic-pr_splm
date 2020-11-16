package ic.unicamp.splm.cli.cmd.graph.pr;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "show-prm")
public class ShowPrM implements Runnable {
    public static final String command_name = "show-prm";

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        splMgr.showPrM();
    }
}
