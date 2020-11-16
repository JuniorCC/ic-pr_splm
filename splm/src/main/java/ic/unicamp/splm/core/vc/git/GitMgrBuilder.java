package ic.unicamp.splm.core.vc.git;

public class GitMgrBuilder {
    private static GitMgr manager = null;

    private GitMgrBuilder() {
    }

    public static synchronized GitMgr getSingletonInstance() {
        if (manager == null) manager = new GitMgr();
        return manager;
    }
}
