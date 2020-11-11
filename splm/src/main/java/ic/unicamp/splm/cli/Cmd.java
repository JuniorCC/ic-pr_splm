package ic.unicamp.splm.cli;

import com.github.lalyos.jfiglet.FigletFont;
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
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static ic.unicamp.splm.cli.util.CmdTag.*;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;

@CommandLine.Command(
    header = {""},
    description = {"", "An SPL manager that internally use git", ""},
    subcommands = {

      // basic
      Exit.class,
      Init.class, // dir
      Version.class,

      // data
      ClearData.class,
      LoadData.class,
      SaveData.class,

      // dir
      RemoveDir.class,

      // git
      Checkout.class,
      Pack.class,
      Status.class,

      // graph -> br
      ShowBrM.class,

      // graph -> fm -> feature
      AddFeature.class,

      // graph -> fm
      InitFM.class,
      ShowFM.class,

      // graph -> map
      ShowMpM.class,

      // graph -> pr -> product
      AddProduct.class,

      // graph -> pr
      ShowPrM.class,

      // graph
      FulfillGraph.class,

      // spl
      CheckConflict.class,
      GenerateBranches.class,
    })
public class Cmd implements Runnable {

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
    CommandLine cmd_load = new CommandLine(new LoadData());
    SplMgrLogger.info(INF_0__SPLM_AUTHOR, true);
    cmd_version.execute();
    __print_scanning_files();
    cmd_load.execute();
    __print_end_scanning_files();
  }

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
              CommandLine commandLine = new CommandLine(new Exit());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_INIT:
            {
              CommandLine commandLine = new CommandLine(new Init());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_VERSION:
            {
              CommandLine commandLine = new CommandLine(new Version());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // data

          case CMD_CLEAR_DATA:
            {
              CommandLine commandLine = new CommandLine(new ClearData());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_LOAD_DATA:
            {
              CommandLine commandLine = new CommandLine(new LoadData());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SAVE_DATA:
            {
              CommandLine commandLine = new CommandLine(new SaveData());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // dir

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
          case CMD_SHOW_BRM:
            {
              CommandLine commandLine = new CommandLine(new ShowBrM());
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
            // graph -> fm
          case CMD_INIT_FM:
            {
              CommandLine commandLine = new CommandLine(new InitFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
          case CMD_SHOW_FM:
            {
              CommandLine commandLine = new CommandLine(new ShowFM());
              __execute_cmd(inputs, commandLine);
              break;
            }
            // graph -> mp
          case CMD_SHOW_MPM:
            {
              CommandLine commandLine = new CommandLine(new ShowMpM());
              __execute_cmd(inputs, commandLine);
              break;
            }

            // graph -> pr -> product
          case CMD_ADD_PROD:
            {
              CommandLine commandLine = new CommandLine(new AddProduct());
              __execute_cmd(inputs, commandLine);
              break;
            }
            // graph -> pr
          case CMD_SHOW_PRM:
            {
              CommandLine commandLine = new CommandLine(new ShowPrM());
              __execute_cmd(inputs, commandLine);
              break;
            }
            // graph
          case CMD_FULFILL_GRAPH:
            {
              CommandLine commandLine = new CommandLine(new FulfillGraph());
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
