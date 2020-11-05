package ic.unicamp.splm.core;

import ic.unicamp.splm.core.data.DataManager;
import ic.unicamp.splm.core.data.DataManagerBuilder;
import ic.unicamp.splm.core.vc.git.GitMgr;
import ic.unicamp.splm.core.vc.git.GitMgrBuilder;

public class SplMgr {
  DataManager dataManager;
  GitMgr gitMgr;

  public SplMgr() {
    dataManager = DataManagerBuilder.getSingletonInstance();
    gitMgr = GitMgrBuilder.getSingletonInstance();
  }

  public void loadData() {
    dataManager.loadData();
  }

  public void saveData() {
    dataManager.saveData();
  }
}
