package ic.unicamp.splm.cli.util;

import ic.unicamp.splm.cli.cmd.basic.*;
import ic.unicamp.splm.cli.cmd.data.ClearData;
import ic.unicamp.splm.cli.cmd.data.LoadData;
import ic.unicamp.splm.cli.cmd.data.SaveData;
import ic.unicamp.splm.cli.cmd.dir.RemoveDir;
import ic.unicamp.splm.cli.cmd.git.Checkout;
import ic.unicamp.splm.cli.cmd.git.Pack;
import ic.unicamp.splm.cli.cmd.git.Status;
import ic.unicamp.splm.cli.cmd.graph.br.*;
import ic.unicamp.splm.cli.cmd.graph.pr.*;
import ic.unicamp.splm.cli.cmd.graph.fm.InitFM;
import ic.unicamp.splm.cli.cmd.graph.fm.ShowFM;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.AddConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.EditConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.ListConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.RemoveConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.feature.*;
import ic.unicamp.splm.cli.cmd.graph.mp.*;
import ic.unicamp.splm.cli.cmd.spl.CheckConflict;
import ic.unicamp.splm.cli.cmd.spl.GenerateBranches;

public interface CmdTag {
  // basic
  String CMD_CLEAR_DATA = ClearData.command_name;
  String CMD_EXIT = Exit.command_name;
  String CMD_INIT = Init.command_name; // dir
  String CMD_LOAD_DATA = LoadData.command_name; // data
  String CMD_SAVE_DATA = SaveData.command_name; // data
  String CMD_VERSION = Version.command_name;

  // directory

  String CMD_REMOVE_DIR = RemoveDir.command_name;

  // git
  String CMD_CHECKOUT = Checkout.command_name;
  String CMD_PACK = Pack.command_name;
  String CMD_STATUS = Status.command_name;

  // data->br
  String CMD_SHOW_BRM = ShowBrM.command_name;

  // graph->conf
  String CMD_ADD_PROD_CONF = AddProduct.command_name;
  String CMD_GENERATE_CONF_TABLE = GenerateConfTable.command_name;
  String CMD_LOAD_CONF = LoadConf.command_name;
  String CMD_REMOVE_PROD_CONF = RemoveProdConf.command_name;
  String CMD_SAVE_CONF = SaveConf.command_name;
  String CMD_SHOW_CONF = ShowPrM.command_name;

  // graph->fm->constraint
  String CMD_ADD_CONSTRAINT = AddConstraint.command_name;
  String CMD_EDIT_CONSTRAINT = EditConstraint.command_name;
  String CMD_LIST_CONSTRAINT = ListConstraint.command_name;
  String CMD_REMOVE_CONSTRAINT = RemoveConstraint.command_name;

  // graph->fm->feature
  String CMD_ADD_FEATURE = AddFeature.command_name;
  String CMD_CHANGE_FEATURE_TYPE = ChangeFeatureType.command_name;
  String CMD_MOVE_FEATURE = MoveFeature.command_name;
  String CMD_REMOVE_FEATURE = RemoveFeature.command_name;
  String CMD_RENAME_FEATURE = RenameFeature.command_name;

  // graph->fm
  String CMD_INIT_FM = InitFM.command_name;
  String CMD_LOAD_FM = LoadFM.command_name;
  String CMD_SAVE_FM = SaveFM.command_name;
  String CMD_SHOW_FM = ShowFM.command_name;
  // String CMD_SHOW_FM_GRAPH = ShowFMGraph.command_name;

  // graph->map
  String CMD_GENERATE_MAP_GRAPH = GenerateMap.command_name;
  String CMD_LOAD_MAP = LoadMap.command_name;
  String CMD_SAVE_MAP = SaveMap.command_name;
  String CMD_SHOW_MAP = ShowMpM.command_name;
  String CMD_SHOW_MAP_GRAPH = ShowMapGraph.command_name;

  // spl
  String CMD_CHECK_CONFLICT = CheckConflict.command_name;
  String CMD_GENERATE_BRANCHES = GenerateBranches.command_name;
  String CMD_GENERATE_PRODUCTS = GenerateProducts.command_name;
  String CMD_LIST_PRODUCTS = ListProducts.command_name;
}
