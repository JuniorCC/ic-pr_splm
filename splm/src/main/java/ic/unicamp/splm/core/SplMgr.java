package ic.unicamp.splm.core;

import ic.unicamp.splm.core.data.DataManager;
import ic.unicamp.splm.core.data.DataManagerBuilder;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureMode;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureType;
import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.dir.SplmDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import ic.unicamp.splm.core.vc.git.GitMgr;
import ic.unicamp.splm.core.vc.git.GitMgrBuilder;

import java.util.List;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WARN_3__WE_COULD_NOT_CREATE_GIT_DIR_BECAUSE_ALREADY_EXITS;

public class SplMgr {
  DataManager dataManager;
  GitMgr gitMgr;

  public SplMgr() {
    dataManager = DataManagerBuilder.getSingletonInstance();
    gitMgr = GitMgrBuilder.getSingletonInstance();
  }

  private void __addFeature(
      String parent, String name, FeatureType featureType, FeatureMode featureMode) {
    dataManager.addFeature(parent, name, featureType, featureMode);
  }

  public void addOptionalFeature(String parent, String name, FeatureMode featureMode) {
    __addFeature(parent, name, FeatureType.OPTIONAL, featureMode);
  }

  public void addMandatoryFeature(String parent, String name, FeatureMode featureMode) {
    __addFeature(parent, name, FeatureType.MANDATORY, featureMode);
  }

  public void addOrFeature(String parent, String name, FeatureMode featureMode) {
    __addFeature(parent, name, FeatureType.OR, featureMode);
  }

  public void addAlternativeFeature(String parent, String name, FeatureMode featureMode) {
    __addFeature(parent, name, FeatureType.ALTERNATIVE, featureMode);
  }

  public void initFM(String name) {
    dataManager.addRootFeature(name, FeatureType.MANDATORY, FeatureMode.CONCRETE);
  }

  public void removeAllDirs() {
    removeGitDir();
    removeSplmDir();
  }

  public void removeGitDir() {
    if (GitDir.exists_git_dir()) {
      SplMgrLogger.info(INFO_3__GIT_DIR_DETECTED, true);
      GitDir.remove_git_dir_with_msg();
    } else {
      SplMgrLogger.info(INFO_3__GIT_DIR_NOT_DETECTED, true);
    }
  }

  public void removeSplmDir() {
    if (SplmDir.exists_splm_dir()) {
      SplMgrLogger.info(INFO_3__SPLM_DIR_DETECTED, true);
      SplmDir.remove_splm_dir_with_msg();
    } else {
      SplMgrLogger.info(INFO_3__SPLM_DIR_NOT_DETECTED, true);
    }
  }

  public void loadData() {
    dataManager.loadData();
  }

  public void saveData() {
    dataManager.saveData();
  }

  public void createHardAllDirs() {
    removeAllDirs();
    SplmDir.create_splm_dir_with_msg();
    GitDir.create_git_dir_with_msg();
  }

  public void createHardSplmDir() {
    if (SplmDir.exists_splm_dir()) {
      SplMgrLogger.info(INFO_3__SPLM_DIR_DETECTED, true);
      SplmDir.remove_splm_dir_with_msg();
    }
    SplmDir.create_splm_dir_with_msg();
  }

  public void createSoftGitDir() {
    if (!GitDir.exists_git_dir()) {
      GitDir.create_git_dir_with_msg();
    } else {
      SplMgrLogger.info(INFO_3__GIT_DIR_DETECTED, true);
      SplMgrLogger.warn(WARN_3__WE_COULD_NOT_CREATE_GIT_DIR_BECAUSE_ALREADY_EXITS, true);
    }
  }

  public boolean existsSplmDir() {
    return SplmDir.exists_splm_dir();
  }

  public void createSplmDir() {
    SplmDir.create_splm_dir_with_msg();
  }

  public void initGit() {
    gitMgr.init();
  }

  public void showRawFM() {
    dataManager.showRawFM();
  }

  public void showFM() {
    dataManager.showFM();
  }

  public void clearData() {
    dataManager.clearData();
  }

  public void showBrM() {
    dataManager.showBrModel();
  }

  public void showRawBrM() {
    dataManager.showRawBrModel();
  }

  public void showPrM() {
    dataManager.showPrM();
  }

  public void fulfillGraph() {
    dataManager.fulfillGraph();
  }

  public void genGitBranches() {
    dataManager.genGitBranches(gitMgr);
  }

  public boolean existsGitDir() {
    return GitDir.exists_git_dir();
  }

  public void addProduct(String name, List<String> features) {
    dataManager.addProduct(name, features);
    SplMgrLogger.info(INFO_0__PRODUCT_ADDED, true);
  }

  public boolean verifyFeature(String feature) {
    return dataManager.verifyFeature(feature);
  }
}
