package ic.unicamp.splm.cli;

import com.github.lalyos.jfiglet.FigletFont;
import ic.unicamp.splm.cli.cmd.*;
import ic.unicamp.splm.cli.cmd.dir.RemoveDir;
import ic.unicamp.splm.cli.cmd.git.Checkout;
import ic.unicamp.splm.cli.cmd.git.Pack;
import ic.unicamp.splm.cli.cmd.git.Status;
import ic.unicamp.splm.cli.cmd.graph.br.*;
import ic.unicamp.splm.cli.cmd.graph.conf.*;
import ic.unicamp.splm.cli.cmd.graph.fm.InitFM;
import ic.unicamp.splm.cli.cmd.graph.fm.LoadFM;
import ic.unicamp.splm.cli.cmd.graph.fm.SaveFM;
import ic.unicamp.splm.cli.cmd.graph.fm.ShowFM;
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
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static ic.unicamp.splm.cli.util.CmdTag.*;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;

@CommandLine.Command(
    header = {},
    description = {"", "An SPL manager that internally use git", ""},
    subcommands = {

      // directory
      Init.class,
      Save.class,
      Load.class,
      RemoveDir.class,

      // git
      Checkout.class,
      // Commit.class,
      Pack.class,
      Status.class,

      // graph -> br
      GenerateBrGraph.class,
      LoadBr.class,
      SaveBr.class,
      ShowBr.class,
      ShowBrGraph.class,

      // graph -> conf
      AddProdConf.class,
      GenerateConfTable.class,
      LoadConf.class,
      RemoveProdConf.class,
      SaveConf.class,
      ShowConf.class,

      // graph -> fm -> constraint
      AddConstraint.class,
      EditConstraint.class,
      ListConstraint.class,
      RemoveConstraint.class,

      // graph -> fm -> feature
      AddFeature.class,
      ChangeFeatureType.class,
      MoveFeature.class,
      RemoveFeature.class,
      RenameFeature.class,

      // graph -> fm
      InitFM.class,
      LoadFM.class,
      SaveFM.class,
      ShowFM.class,
      // ShowFMGraph.class,

      // graph -> map
      GenerateMapGraph.class,
      LoadMap.class,
      SaveMap.class,
      ShowMap.class,
      ShowMapGraph.class,

      // spl
      CheckConflict.class,
      GenerateBranches.class,
      GenerateProducts.class,
      ListProducts.class,

      // basic
      Clear.class,
      Version.class,
    })
public class Cmd implements Runnable {

  // Logger logger = LoggerFactory.getLogger(XGitCommands.class);

  @Override
  public void run() {
    String asciiArt = FigletFont.convertOneLine(INF_0__SPLM_ART_ASCII);
    SplMgrLogger.message(asciiArt, true);
    SplMgrLogger.message_ln(INF_0__WELCOME_SPLM, false);
    __run_default_commands();
    __run_splm_prompt();
  }

  private void __run_default_commands() {
    CommandLine cmd_version = new CommandLine(new Version());
    CommandLine cmd_load = new CommandLine(new Load());
    SplMgrLogger.info(INF_0__SPLM_AUTHOR, true);
    cmd_version.execute();
    __print_scanning_files();
    cmd_load.execute();
    __print_end_scanning_files();
  }

  /*  @CommandLine.Command(name = "prompt")
  void prompt_command() {
    //CommandLine commandLine = new CommandLine(new Cmd());
    String asciiArt = FigletFont.convertOneLine("SMPL");
    SplMgrLogger.info(asciiArt, false);
    SplMgrLogger.message(INF_0__WELCOME_SPLM, false);
    __run_splm_prompt();
  }*/
  private void __run_splm_prompt() {
    boolean alive = true;
    Scanner sc = new Scanner(System.in);
    while (alive) {
      __print_prompt();
      String line = sc.nextLine();
      List<String> inputs = new LinkedList<>(Arrays.asList(line.split(" ")));
      if (!line.isEmpty()) {
        String command = inputs.get(0);
        switch (command) {
            // basic
          case CMD_EXIT:
            {
              alive = false;
              __execute_cmd(inputs, null);
              break;
            }
          case CMD_CLEAR:
            {
              CommandLine commandLine = new CommandLine(new Clear());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_VERSION:
            {
              CommandLine commandLine = new CommandLine(new Version());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE:
          {
            CommandLine commandLine = new CommandLine(new Save());
            __execute_cmd(inputs, commandLine);
            break;
          }
          case CMD_LOAD:
          {
            CommandLine commandLine = new CommandLine(new Load());
            __execute_cmd(inputs, commandLine);
            break;
          }
            // directory
          case CMD_INIT:
            {
              CommandLine commandLine = new CommandLine(new Init());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_REMOVE_DIR:
            {
              CommandLine commandLine = new CommandLine(new RemoveDir());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // git
          case CMD_CHECKOUT:
            {
              CommandLine commandLine = new CommandLine(new Checkout());
              __execute_cmd(inputs, commandLine);
              break;
            }
            // case COMMIT: {
            //    CommandLine commandLine = new CommandLine(new Commit());
            //    execute_command(inputs, commandLine);
            //    break;
            // }
          case CMD_PACK:
            {
              CommandLine commandLine = new CommandLine(new Pack());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_STATUS:
            {
              CommandLine commandLine = new CommandLine(new Status());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> br
          case CMD_GENERATE_BR_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new GenerateBrGraph());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LOAD_BR:
            {
              CommandLine commandLine = new CommandLine(new LoadBr());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE_BR:
            {
              CommandLine commandLine = new CommandLine(new SaveBr());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_BR:
            {
              CommandLine commandLine = new CommandLine(new ShowBr());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_BR_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new ShowBrGraph());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> conf
          case CMD_ADD_PROD_CONF:
            {
              CommandLine commandLine = new CommandLine(new AddProdConf());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_GENERATE_CONF_TABLE:
            {
              CommandLine commandLine = new CommandLine(new GenerateConfTable());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LOAD_CONF:
            {
              CommandLine commandLine = new CommandLine(new LoadConf());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_REMOVE_PROD_CONF:
            {
              CommandLine commandLine = new CommandLine(new RemoveProdConf());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE_CONF:
            {
              CommandLine commandLine = new CommandLine(new SaveConf());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_CONF:
            {
              CommandLine commandLine = new CommandLine(new ShowConf());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> fm -> constraint
          case CMD_ADD_CONSTRAINT:
            {
              CommandLine commandLine = new CommandLine(new AddConstraint());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_EDIT_CONSTRAINT:
            {
              CommandLine commandLine = new CommandLine(new EditConstraint());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LIST_CONSTRAINT:
            {
              CommandLine commandLine = new CommandLine(new ListConstraint());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_REMOVE_CONSTRAINT:
            {
              CommandLine commandLine = new CommandLine(new RemoveConstraint());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> fm -> feature
          case CMD_ADD_FEATURE:
            {
              CommandLine commandLine = new CommandLine(new AddFeature());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_CHANGE_FEATURE_TYPE:
            {
              CommandLine commandLine = new CommandLine(new ChangeFeatureType());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_MOVE_FEATURE:
            {
              CommandLine commandLine = new CommandLine(new MoveFeature());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_REMOVE_FEATURE:
            {
              CommandLine commandLine = new CommandLine(new RemoveFeature());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_RENAME_FEATURE:
            {
              CommandLine commandLine = new CommandLine(new RenameFeature());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> fm
          case CMD_INIT_FM:
            {
              CommandLine commandLine = new CommandLine(new InitFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LOAD_FM:
            {
              CommandLine commandLine = new CommandLine(new LoadFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE_FM:
            {
              CommandLine commandLine = new CommandLine(new SaveFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_FM:
            {
              CommandLine commandLine = new CommandLine(new ShowFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
            /*case CMD_SHOW_FM_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new ShowFMGraph());
              __execute_cmd(inputs, commandLine);
              break;
            }*/

            // graph -> map
          case CMD_GENERATE_MAP_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new GenerateMapGraph());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LOAD_MAP:
            {
              CommandLine commandLine = new CommandLine(new LoadMap());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE_MAP:
            {
              CommandLine commandLine = new CommandLine(new SaveMap());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_MAP:
            {
              CommandLine commandLine = new CommandLine(new ShowMap());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_MAP_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new ShowMapGraph());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // spl
          case CMD_CHECK_CONFLICT:
            {
              CommandLine commandLine = new CommandLine(new CheckConflict());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_GENERATE_BRANCHES:
            {
              CommandLine commandLine = new CommandLine(new GenerateBranches());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_GENERATE_PRODUCTS:
            {
              CommandLine commandLine = new CommandLine(new GenerateProducts());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LIST_PRODUCTS:
            {
              CommandLine commandLine = new CommandLine(new ListProducts());
              __execute_cmd(inputs, commandLine);
              break;
            }
          default:
            __print_cmd_not_valid();
            break;
        }
      }
    }
  }

  private void __execute_cmd(List<String> inputs, CommandLine command) {
    __print_cmd_valid();
    if (command != null) {
      inputs.remove(0); // remove the command from the arguments
      if (inputs.isEmpty()) {
        command.execute();
      } else {
        String[] new_args = inputs.toArray(new String[0]);
        command.execute(new_args); // send arguments
      }
    }
    __print_cmd_end();
  }

  private void __print_scanning_files() {
    SplMgrLogger.message_ln(INF_0__SCANNING_FILES, false);
  }

  private void __print_end_scanning_files() {
    SplMgrLogger.message_ln(INF_0__END_SCANNING_FILES, false);
  }

  private void __print_cmd_valid() {
    SplMgrLogger.message_ln(INF_0__CMD_ACCEPTED, false);
  }

  private void __print_cmd_end() {
    SplMgrLogger.message_ln(INF_0__CMD_END, false);
  }

  private void __print_cmd_not_valid() {
    SplMgrLogger.message_ln(INF_0__CMD_NOT_VALID, false);
  }

  private void __print_prompt() {
    SplMgrLogger.message(INF_0__PROMPT, false);
  }
}
