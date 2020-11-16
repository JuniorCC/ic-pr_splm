package ic.unicamp.splm.cli.cmd.data;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "load-data")
public class LoadData implements Runnable {
    public static final String command_name = "load-data";

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        splMgr.loadData();
    }
}
