package ic.unicamp.splm.cli.util;

import ic.unicamp.splm.cli.cmd.basic.Exit;
import ic.unicamp.splm.cli.cmd.basic.Init;
import ic.unicamp.splm.cli.cmd.basic.Version;
import ic.unicamp.splm.cli.cmd.data.ClearData;
import ic.unicamp.splm.cli.cmd.data.LoadData;
import ic.unicamp.splm.cli.cmd.data.SaveData;
import ic.unicamp.splm.cli.cmd.dir.RemoveDir;
import ic.unicamp.splm.cli.cmd.git.Checkout;
import ic.unicamp.splm.cli.cmd.git.Pack;
import ic.unicamp.splm.cli.cmd.git.Status;
import ic.unicamp.splm.cli.cmd.graph.FulfillGraph;
import ic.unicamp.splm.cli.cmd.graph.br.ShowBrM;
import ic.unicamp.splm.cli.cmd.graph.fm.InitFM;
import ic.unicamp.splm.cli.cmd.graph.fm.ShowFM;
import ic.unicamp.splm.cli.cmd.graph.fm.feature.AddFeature;
import ic.unicamp.splm.cli.cmd.graph.mp.ShowMpM;
import ic.unicamp.splm.cli.cmd.graph.pr.ShowPrM;
import ic.unicamp.splm.cli.cmd.graph.pr.product.AddProduct;
import ic.unicamp.splm.cli.cmd.spl.CheckConflict;
import ic.unicamp.splm.cli.cmd.spl.GenerateBranches;

public interface CmdTag {
    // basic
    String CMD_EXIT = Exit.command_name;
    String CMD_INIT = Init.command_name; // dir
    String CMD_VERSION = Version.command_name;

    // data
    String CMD_CLEAR_DATA = ClearData.command_name;
    String CMD_LOAD_DATA = LoadData.command_name;
    String CMD_SAVE_DATA = SaveData.command_name;

    // dir
    String CMD_REMOVE_DIR = RemoveDir.command_name;

    // git
    String CMD_CHECKOUT = Checkout.command_name;
    String CMD_PACK = Pack.command_name;
    String CMD_STATUS = Status.command_name;

    // graph->br (branch)
    String CMD_SHOW_BRM = ShowBrM.command_name;

    // graph->fm->feature
    String CMD_ADD_FEATURE = AddFeature.command_name;

    // graph->fm (feature)
    String CMD_INIT_FM = InitFM.command_name;
    String CMD_SHOW_FM = ShowFM.command_name;

    // graph->mp (map)
    String CMD_SHOW_MPM = ShowMpM.command_name;

    // graph->pr->product (product)
    String CMD_ADD_PROD = AddProduct.command_name;
    // graph->pr (product)
    String CMD_SHOW_PRM = ShowPrM.command_name;

    // graph
    String CMD_FULFILL_GRAPH = FulfillGraph.command_name;

    // spl
    String CMD_CHECK_CONFLICT = CheckConflict.command_name;
    String CMD_GENERATE_BRANCHES = GenerateBranches.command_name;
}
