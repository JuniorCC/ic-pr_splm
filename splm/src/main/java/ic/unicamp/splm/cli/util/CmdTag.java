package ic.unicamp.splm.cli.util;

import ic.unicamp.splm.cli.cmd.Clear;
import ic.unicamp.splm.cli.cmd.Version;
import ic.unicamp.splm.cli.cmd.dir.FileStatus;
import ic.unicamp.splm.cli.cmd.dir.Init;
import ic.unicamp.splm.cli.cmd.dir.RemoveDir;
import ic.unicamp.splm.cli.cmd.git.Checkout;
import ic.unicamp.splm.cli.cmd.git.Pack;
import ic.unicamp.splm.cli.cmd.git.Status;
import ic.unicamp.splm.cli.cmd.graph.br.*;
import ic.unicamp.splm.cli.cmd.graph.conf.*;
import ic.unicamp.splm.cli.cmd.graph.fm.*;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.AddConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.EditConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.ListConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.constraint.RemoveConstraint;
import ic.unicamp.splm.cli.cmd.graph.fm.feature.*;
import ic.unicamp.splm.cli.cmd.graph.map.*;
import ic.unicamp.splm.cli.cmd.spl.CheckConflict;
import ic.unicamp.splm.cli.cmd.spl.GenerateBranches;
import ic.unicamp.splm.cli.cmd.spl.GenerateProducts;
import ic.unicamp.splm.cli.cmd.spl.ListProducts;

public interface CmdTag {
  // basic
  String CMD_EXIT = "exit";
  String CMD_CLEAR = Clear.command_name;
  String CMD_VERSION = Version.command_name;

  // directory
  String CMD_FILE_STATUS = FileStatus.command_name;
  String CMD_INIT = Init.command_name;
  String CMD_REMOVE_DIR = RemoveDir.command_name;

  // git
  String CMD_CHECKOUT = Checkout.command_name;
  // String CMD_COMMIT = Commit.command_name;
  String CMD_PACK = Pack.command_name;
  String CMD_STATUS = Status.command_name;

  // graph->br
  String CMD_GENERATE_BR_GRAPH = GenerateBrGraph.command_name;
  String CMD_LOAD_BR = LoadBr.command_name;
  String CMD_SAVE_BR = SaveBr.command_name;
  String CMD_SHOW_BR = ShowBr.command_name;
  String CMD_SHOW_BR_GRAPH = ShowBrGraph.command_name;

  // graph->conf
  String CMD_ADD_PROD_CONF = AddProdConf.command_name;
  String CMD_GENERATE_CONF_TABLE = GenerateConfTable.command_name;
  String CMD_LOAD_CONF = LoadConf.command_name;
  String CMD_REMOVE_PROD_CONF = RemoveProdConf.command_name;
  String CMD_SAVE_CONF = SaveConf.command_name;
  String CMD_SHOW_CONF = ShowConf.command_name;

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
  String CMD_SHOW_FM_GRAPH = ShowFMGraph.command_name;

  // graph->map
  String CMD_GENERATE_MAP_GRAPH = GenerateMapGraph.command_name;
  String CMD_LOAD_MAP = LoadMap.command_name;
  String CMD_SAVE_MAP = SaveMap.command_name;
  String CMD_SHOW_MAP = ShowMap.command_name;
  String CMD_SHOW_MAP_GRAPH = ShowMapGraph.command_name;

  // spl
  String CMD_CHECK_CONFLICT = CheckConflict.command_name;
  String CMD_GENERATE_BRANCHES = GenerateBranches.command_name;
  String CMD_GENERATE_PRODUCTS = GenerateProducts.command_name;
  String CMD_LIST_PRODUCTS = ListProducts.command_name;
}
