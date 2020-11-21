package ic.unicamp.splm.cli.cmd.spl;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "check-conflict")
public class CheckConflict implements Runnable {
    public static final String command_name = "check-conflict";

    @CommandLine.Parameters(paramLabel = "from", description = "from", arity = "0..1")
    String from;

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        if(from == null){
            splMgr.checkConflict();
        }else{
            splMgr.checkConflict(from);
        }
    }
}
