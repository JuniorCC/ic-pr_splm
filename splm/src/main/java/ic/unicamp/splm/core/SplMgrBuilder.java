package ic.unicamp.splm.core;

public class SplMgrBuilder {
  private static SplMgr manager = null;

  private SplMgrBuilder() {}

  public static synchronized SplMgr getSingletonInstance() {
    if (manager == null) manager = new SplMgr();
    return manager;
  }
}
