package ic.unicamp.splm.cli.cmd.graph.pr.product;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import java.util.LinkedList;
import java.util.List;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INFO_0__FEATURE_FOUND;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__FEATURE_NOT_FOUND;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__THERE_IS_NOT_SELECTED_FEATURE_TO_RELATE_WITH_THE_PRODUCT;

@CommandLine.Command(name = "add-product")
public class AddProduct implements Runnable {
  public static final String command_name = "add-product";

  @CommandLine.Parameters(paramLabel = "name", description = "product name")
  String name;

  @CommandLine.Parameters(
      paramLabel = "features",
      index = "1..",
      description = "one ore more files to archive")
  String[] features;


  @Override
  public void run() {
    SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
    List<String> verified_features = new LinkedList<>();
    for (String feature : features) {
      if (splMgr.verifyFeature(feature)) {
        verified_features.add(feature);
        SplMgrLogger.info(String.format(INFO_0__FEATURE_FOUND, feature) , true);
      }else{
        SplMgrLogger.warn(String.format(WAR_0__FEATURE_NOT_FOUND, feature) , true);
      }
    }
    if(!verified_features.isEmpty()){
        splMgr.addProduct(name, verified_features);
    }else{
      SplMgrLogger.warn(WAR_0__THERE_IS_NOT_SELECTED_FEATURE_TO_RELATE_WITH_THE_PRODUCT , true);
    }

  }
}
