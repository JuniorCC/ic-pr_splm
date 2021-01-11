package ic.unicamp.splm.core.vc;

public class VCMgrBuilder {
    private static VCMgr manager = null;

    private VCMgrBuilder() {
    }

    public static synchronized VCMgr getSingletonInstance() {
        if (manager == null) manager = new VCMgr();
        return manager;
    }
}
