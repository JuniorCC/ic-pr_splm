package ic.unicamp.splm.cli.cmd.data;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "search-vc")
public class SearchVC implements Runnable {
    public static final String command_name = "search-vc";

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        splMgr.searchVCDirectories();
    }
}
