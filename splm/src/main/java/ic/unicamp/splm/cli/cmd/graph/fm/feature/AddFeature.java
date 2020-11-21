package ic.unicamp.splm.cli.cmd.graph.fm.feature;

import ic.unicamp.splm.core.SplMgr;
import ic.unicamp.splm.core.SplMgrBuilder;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureMode;
import picocli.CommandLine;

@CommandLine.Command(name = "add-feature")
public class AddFeature implements Runnable {
    public static final String command_name = "add-feature";

    @CommandLine.Parameters(paramLabel = "parent", description = "parent feature")
    String parent;

    @CommandLine.Parameters(
            paramLabel = "names",
            index = "1..",
            description = "one ore more name of the feature")
    String[] names;

/*    @CommandLine.Parameters(paramLabel = "name", description = "name of the feature")
    String name;*/

    @CommandLine.Option(names = "-optional", description = "'create-feature -optional'")
    private boolean optional;

    @CommandLine.Option(names = "-mandatory", description = "'create-feature -mandatory'")
    private boolean mandatory;

    @CommandLine.Option(names = "-or", description = "'create-feature -or'")
    private boolean or;

    @CommandLine.Option(names = "-alternative", description = "'create-feature -alternative'")
    private boolean alternative;

    @CommandLine.Option(names = "-a", description = "'create-feature -abstract'")
    private boolean abs;

    @Override
    public void run() {
        SplMgr splMgr = SplMgrBuilder.getSingletonInstance();
        FeatureMode featureMode = FeatureMode.CONCRETE;

        if (abs) {
            featureMode = FeatureMode.ABSTRACT;
        }
        if (optional) {
            for (String name : names) {
                splMgr.addOptionalFeature(parent, name, featureMode);
            }
            return;
        }
        if (mandatory) {
            for (String name : names) {
                splMgr.addMandatoryFeature(parent, name, featureMode);
            }
            return;
        }
        if (or) {
            for (String name : names) {
                splMgr.addOrFeature(parent, name, featureMode);
            }
            return;
        }
        if (alternative) {
            for (String name : names) {
                splMgr.addAlternativeFeature(parent, name, featureMode);
            }
            return;
        }
        // default
        for (String name : names) {
            splMgr.addMandatoryFeature(parent, name, featureMode);
        }
    }
}
